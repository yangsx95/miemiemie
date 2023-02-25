docker run \
  -p 9000:9000 \
  -p 9001:9001 \
  --name minio \
  -v ~/docker/data:/data \
  -e "MINIO_ROOT_USER=minioadmin" \
  -e "MINIO_ROOT_PASSWORD=minioadmin" \
  minio/minio server /data --console-address ":9001"