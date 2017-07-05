
path_to_servicemix=$HOME"/Software/servicemix/deploy/"

echo "Script mvn clean package..."
mvn clean install package
echo "Starting deployment..."

cp droneexpress-drone/target/droneexpress.drone-1.0.jar $path_to_servicemix
cp droneexpress-flightplan/target/droneexpress.flightplan-1.0.jar $path_to_servicemix
cp droneexpress-itinerary/target/droneexpress.itenerary-1.0.jar  $path_to_servicemix
cp droneexpress-warehouse/target/droneexpress.warehouse-1.0.jar $path_to_servicemix
echo "Build and deployed"
