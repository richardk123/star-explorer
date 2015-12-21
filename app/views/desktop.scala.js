@(keyConnector: String)

$(function init() {

    var scene, camera, renderer, player;
    var keyConnector = @keyConnector;

    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
    var desktopSocket = new WS("@routes.Desktop.ws(keyConnector).webSocketURL(request)")

    var planes = {};

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
            if (data.keyConnector == keyConnector)
            {
                player.rotation.x = data.angleX;
                player.rotation.y = data.angleY;
                player.rotation.z = data.angleZ;

                player.position.x += data.vX;
                player.position.y += data.vY;
                player.position.z += data.vZ;
            }

        }
    };

    function initThee()
    {
        scene = new THREE.Scene();
        camera = new THREE.PerspectiveCamera( 75, window.innerWidth/window.innerHeight, 0.1, 1000 );
        camera.position.y = 2.5;
        camera.position.z = 5;

        // create player
        player = createCube(0, 0, 0, false, 0xFF0000);
        player.add(camera);
        scene.add(player);

        renderer = new THREE.WebGLRenderer({ antialias: true });
        renderer.setSize( window.innerWidth, window.innerHeight );
        document.body.appendChild(renderer.domElement );

        var light = new THREE.AmbientLight( 0x404040 );
        scene.add(light);

        var directionalLight = new THREE.DirectionalLight( 0x404040 );
        directionalLight.position.x = 5;
        directionalLight.position.y = 3;
        directionalLight.position.z = 4;
        directionalLight.position.normalize();
        scene.add(directionalLight);

        createMap();
        render();
    }

    var render = function () {

        camera.lookAt(new THREE.Vector3(player.position));
        requestAnimationFrame(render);
        renderer.render(scene, camera);
    };

    function createMap() {

        for (z = 2; z < 80; z++) {
            for (x = -2; x <= 2; x++) {
                for (y = -2; y <= 2; y++) {
                    if (Math.random() < z / 180) {
                        scene.add(createCube(x, y, z, true, 0xffffff));
                    }
                }
            }
        }
    }

    function createCube(x, y, z, transparent, color)
    {
        var geometry = new THREE.BoxGeometry( 1, 1, 1 );
        var material = new THREE.MeshLambertMaterial( {
            color: color,
            shading: THREE.FlatShading,
            transparent: transparent,
            opacity: 0.5
        } );
        var cube = new THREE.Mesh( geometry, material );

        cube.position.x = x;
        cube.position.y = y;
        cube.position.z = z;

        return cube;
    }

});



