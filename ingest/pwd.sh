pwd
mongo dpiprobe --eval "printjson(db.dpisignals.drop())"
mongo dpiprobe --eval "printjson(db.dpibase.drop())"
mongoimport -d dpiprobe -c dpisignals --type csv --file device_performance_index.csv --headerline
