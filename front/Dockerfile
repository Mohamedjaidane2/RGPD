FROM nginx:alpine
COPY nginx.conf /etc/nginx/nginx.conf
COPY /dist/rgpd-frontend /usr/share/nginx/html

# Expose the port the app runs in
EXPOSE 80
#specifies the default program that will execute once the container runs
CMD ["nginx", "-g", "daemon off;"]
