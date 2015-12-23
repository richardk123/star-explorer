@(keyConnector: String)

$(function ()
{
	var dataToSend;

	var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket;
	var mobileSocket = new WS("@routes.Mobile.ws(keyConnector).webSocketURL(request)");

	mobileSocket.onmessage = function (event)
	{
		var data = JSON.parse(event.data);
	};

	// register handler
	if (window.DeviceOrientationEvent)
	{
		window.addEventListener('deviceorientation', deviceOrientationHandler, true);
	}
	else
	{
		window.alert("Not supported.");
	}

	function deviceOrientationHandler(eventData)
	{
		dataToSend = JSON.stringify(
			{
				type: "sensorData",
				rotationX: roundNumber(radians(eventData.beta)),
				rotationY: roundNumber(radians(eventData.alpha)),
				rotationZ: roundNumber(radians(eventData.gamma))
			}
		);
	}

	// send data every 20 milliseconds
	setInterval(function ()
	{
		if (dataToSend != undefined)
		{
			mobileSocket.send(dataToSend);
		}
	}, 20);

	$("#speed").click(function() {

		mobileSocket.send(JSON.stringify({type: "boost-speed"}));
	});

	$("#shield").click(function() {

		mobileSocket.send(JSON.stringify({type: "shield-up"}));
	});

	$("#fire-gun").click(function() {

		mobileSocket.send(JSON.stringify({type: "fire-gun"}));
	});

	$("#fire-rocket").click(function() {

		mobileSocket.send(JSON.stringify({type: "fire-rocket"}));
	});

	function roundNumber(number)
	{
		return Math.round(number * 1000) / 1000;
	}

	var radians = function (degrees)
	{
		return degrees * Math.PI / 180;
	};

});
