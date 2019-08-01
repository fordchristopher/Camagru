import React from "react";
import Fish from "./pictures/fish.png";
import Cracks from "./pictures/cracks_burned.png";
import "./studio.css";

class Stream extends React.Component {
	constructor() {
		super();
		this.state = {
			prop: null
		};
	}
	componentDidMount(){
		const constraints = { video: true };

		const video = document.querySelector('video');
		const canvas = document.querySelector('canvas');
		const context = canvas.getContext('2d');
		let mediaStream = null;

		navigator.mediaDevices.getUserMedia(constraints)
			.then(stream => {
				video.srcObject = stream;
				video.play();
				mediaStream = stream;
			})
			.catch(err => console.error(err));

		document.querySelector("#snap").addEventListener('click', snapshot, false);

		function snapshot() {
			if (mediaStream) {
				context.drawImage(video, 0, 0, video.width, video.height);
				console.log(this.state);
				/*if (this.state.prop != null)
					context.drawImage(document.querySelector(`#{this.state.prop}`), 0, 0, video.width, video.height);
			*/}
		}
	}

	applyProp = (e) => {
		this.setState({
			prop : e.target.id
		});
	}

	render() {
		return (
			<div>
				<img id="Fish" class="prop" src={Fish} alt="city">
				</img>
				<img id="Cracks" class="prop" src={Cracks} alt="city">
				</img>
				<video width="320" height="240"></video>
				<canvas width="320" height="240"></canvas>
				<button onClick={this.register} type='button' id="snap" className="button-primary">Take Photo</button>
			</div>
		);
	}
}
export default Stream;
