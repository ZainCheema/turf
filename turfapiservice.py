import os
import requests
import flask
import pymongo
from flask import request, jsonify
from pymongo import MongoClient

# Helped:
# https://programminghistorian.org/en/lessons/creating-apis-with-python-and-flask#installing-python-and-flask
# https://medium.com/fullstackai/how-to-deploy-a-simple-flask-app-on-cloud-run-with-cloud-endpoint-e10088170eb7

app = flask.Flask(__name__)
app.config["DEBUG"] = True

# List that holds the boxes
boxes = []

# The URL for the MongoDB cluster. Blank for privacy.
db_url = ""

client = MongoClient(db_url)

# Reference to the database in the cluster
db = client.turf_db

# Retrieve all documents, remove ObjectID field
boxes_without_oid = db.get_collection('boxes').find({},{'_id':0})

# Populate boxes list
for box in boxes_without_oid:
    boxes.append(box)

print(boxes)

@app.route('/', methods=["GET"])
def home():
    return jsonify("You have connected to Turf's API! Congrats! Proud of you.")


@app.route('/api/v1/boxes/', methods = ['GET','POST'])
def handle_boxes_request():
    if request.method == 'GET':
        # Called by the app once, at launch 
        return jsonify(boxes)
    
    if request.method == 'POST':
        try:
            # received contents of turf_box object
            json = dict(request.get_json())

            # Update local boxes list
            boxes[json['id']] = json

            # Find the document where there is an id match, change color 
            # helped: https://www.analyticsvidhya.com/blog/2020/08/query-a-mongodb-database-using-pymongo/
            db.get_collection('boxes').find_one_and_replace( {"id": json["id"]}, json)

            return json
        except:
            return "Failed to update database :("
    

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
