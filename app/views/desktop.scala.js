@(keyConnector: String)

$(function init() {

    var scene, camera, renderer, player;
    var keyConnector = '@keyConnector';

    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
    var desktopSocket = new WS("@routes.Desktop.ws(keyConnector).webSocketURL(request)")

    var planes = {};
    var shields = {};

    desktopSocket.onmessage = function(event) {
        var data = JSON.parse(event.data);

        if (data.type === 'mobileConnected')
        {
            $("#connectMobile").hide();
            $("#mobileConnected").show();
            $("#startGame").click(function() {

                $("#mobileConnected").hide();
                initThee();

                var dataToSend = JSON.stringify(
                    {
                        type: "gameStarted"
                    }
                );
                desktopSocket.send(dataToSend);
            });
        }
        else if (data.type === 'playerData')
        {
            var dataPlayer;

            if (planes[data.keyConnector] == undefined)
            {
                dataPlayer = createPlayer(data.x, data.y);
                planes[data.keyConnector] = dataPlayer;
                scene.add(dataPlayer);
            }

            dataPlayer = planes[data.keyConnector];

            dataPlayer.rotation.z = data.angle;
            dataPlayer.position.x += data.vX;
            dataPlayer.position.y += data.vY;
        }
        else if(data.type === 'shield-up')
        {
            var dataPlayer = planes[data.keyConnector];

            var texture = THREE.ImageUtils.loadTexture( '/assets/images/shield.png' );
            var shield = new THREE.Mesh(
                new THREE.PlaneGeometry(2, 2, 0),
                new THREE.MeshBasicMaterial({
                    map: texture
                }));
            dataPlayer.add(shield);
            shields[data.keyConnector] = shield;

        }
        else if(data.type === 'shield-down')
        {
            var dataPlayer = planes[data.keyConnector];
            var shield = shields[data.keyConnector];
            dataPlayer.remove(shield);
            delete shields[data.keyConnector];
        }
    };

    function initThee()
    {
        scene = new THREE.Scene();
        camera = new THREE.PerspectiveCamera( 75, window.innerWidth/window.innerHeight, 0.1, 1000 );
        camera.position.x = 0;
        camera.position.y = 0;
        camera.position.z = 5;

        // Load the background texture
        var texture = THREE.ImageUtils.loadTexture( '/assets/images/universe.jpg' );
        var backgroundMesh = new THREE.Mesh(
            new THREE.PlaneGeometry(71, 10, 0),
            new THREE.MeshBasicMaterial({
                map: texture
            }));
        scene.add(backgroundMesh);

        // create player
        player = createPlayer(0, 0);
        planes[keyConnector] = player;
        player.add(camera);

        renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setSize( window.innerWidth, window.innerHeight );
        document.body.appendChild(renderer.domElement );

        var light = new THREE.AmbientLight( 0x404040 );
        scene.add(light);

        var directionalLight = new THREE.DirectionalLight( 0x404040 );
        directionalLight.position.x = 0;
        directionalLight.position.y = 0;
        directionalLight.position.z = 0;
        directionalLight.position.normalize();
        scene.add(directionalLight);

        render();
    }

    var render = function () {

        camera.lookAt(new THREE.Vector3(player.position));
        requestAnimationFrame(render);
        renderer.render(scene, camera);
    };


    function createPlayer(x, y)
    {
        // Load the background texture
        var texture = THREE.ImageUtils.loadTexture( '/assets/images/player.png' );
        var ship = new THREE.Mesh(
            new THREE.PlaneGeometry(1.7, 1, 0),
            new THREE.MeshBasicMaterial({
                shading: THREE.FlatShading,
                map: texture,
                transparent: true,
                overdraw: true
            }));

        ship.position.x = x;
        ship.position.y = y;

        scene.add(ship);

        return ship;
    }

});



