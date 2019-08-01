import React from "react";
import Fish from "./pictures/fish.png";
import Cracks from "./pictures/cracks_burned.png";
import Hello from "./pictures/Hello.png";
import "./studio.css";

class Stream extends React.Component {
	constructor() {
		super();
		this.state = {
			prop: null,
			photoTaken : false
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

		document.querySelector("#snap").addEventListener('click', () => snapshot(this.state.prop), false);

		function snapshot(id) {
			if (mediaStream) {
				context.drawImage(video, 0, 0, video.width, video.height);
				console.log(id);
				if (id)
					context.drawImage(document.querySelector(`#${id}`), 0, 0, video.width, video.height);
			}
		}
	}

	applyProp = (e) => {
		this.setState({
			prop : e.target.id
		}, () => console.log(this.state));
	}

	allowPublish = () => {
		this.setState({
			photoTaken : true
		}, console.log(this.state));
	}

	renderPublish = () => {
		if (this.state.photoTaken)
		{
			return (
				<button type='button' id="snap" className="button-primary">Save and Publish</button>
			);
		}
	}

	render() {
		return (
			<div>
			<div className="container-props">
				<img id="Fish" onClick={this.applyProp} className="prop" src={Fish} alt="Fish" />
				<img onClick={this.applyProp} id="Cracks" className="prop" src={Cracks} alt="Cracks" />
				<img onClick={this.applyProp} id="Hello" className="prop" src={Hello} alt="Hello" />
			</div>
				<video width="320" height="240"></video>
			<br />
				<button type='button' onClick={this.allowPublish} id="snap" className="button-primary">Take Photo</button>
			<br />
				<canvas width="320" height="240"></canvas>
				<br />
			{this.renderPublish()}
			</div>
		);
	}
}
export default Stream;
