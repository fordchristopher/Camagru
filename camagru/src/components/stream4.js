import React from "react";
import Fish from "./pictures/fish.png";

class Stream extends React.Component {
	constructor() {
		super();
		this.state = {
			test: null
		};
	}
	componentDidMount(){
		const constraints = { video: true };

		const video = document.querySelector('video');
		const canvas = document.querySelector('canvas');
		const context = canvas.getContext('2d');
		let mediaStream = null;

		// call getUserMedia, then the magic
		navigator.mediaDevices.getUserMedia(constraints)
			.then(stream => {
				video.srcObject = stream;
				video.play();
				mediaStream = stream;
			})
			.catch(err => console.error(err));

		video.addEventListener('click', snapshot, false);

		function snapshot() {
			if (mediaStream) {
				context.drawImage(video, 0, 0, video.width, video.height);
				context.drawImage(document.querySelector("#ak"), 0, 0, video.width, video.height);
			}
		}
	}

	render() {
		return (
			<div>
				<img id="ak" src={Fish} alt="city">
				</img>
				<video width="320" height="240"></video>
				<canvas width="320" height="240"></canvas>
			</div>
		);
	}
}
export default Stream;
