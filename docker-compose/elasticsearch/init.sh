#!/bin/bash

set -e

# Function to wait for Elasticsearch to be ready
wait_for_elasticsearch() {
  until curl -sS "http://localhost:9200/_cat/health?h=status" | grep -q "green\|yellow"; do
    echo "Waiting for Elasticsearch to be ready..."
    sleep 2
  done
}

# Wait for Elasticsearch
wait_for_elasticsearch

# Create the index connect-logs using curl
curl -X PUT "http://localhost:9200/connect-logs" -H 'Content-Type: application/json' -d @- <<< '{
  "mappings": {
    "properties": {
      "@timestamp": { "type": "date" },
      "log_level": { "type": "keyword" },
      "trace_id": { "type": "keyword" },
      "logger_name": { "type": "keyword" },
      "class_name": { "type": "keyword" },
      "method_name": { "type": "keyword" },
      "line_number": { "type": "integer" },
      "message": { "type": "text" }
    }
  }
}'

echo "Index created successfully."
