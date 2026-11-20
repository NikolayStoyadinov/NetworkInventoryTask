from postman/newman

WORKDIR /tests

COPY postman/MyCollection.postman_collection ./collection.json

CMD ["run", "collection.json", "--env-var", "baseUrl=http://nis:8080"]
