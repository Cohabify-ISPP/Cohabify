import SockJS from "sockjs-client";
import Stomp from "stompjs";

export function CohabifyStompClient() {

	let socket = new SockJS(import.meta.env.VITE_BACKEND_URL + '/chat-ws');
	let stompClient = Stomp.over(socket);

	socket.onclose = () => {
		console.log('WebSocket connection closed');
	};

	socket.onerror = (error) => {
		console.error('WebSocket error', error);
	};

	stompClient.onWebSocketError = (error) => {
		console.error('Error with websocket', error);
	};

	stompClient.onStompError = (frame) => {
		console.error('Broker reported error: ' + frame.headers['message']);
		console.error('Additional details: ' + frame.body);
	};

	stompClient.connect({
		'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
	}, (frame) => {
	    console.log('Connected: ' + frame);
	});

	return stompClient;
}
