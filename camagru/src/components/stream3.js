import React from "react";

class Stream extends React.Component {
	constructor() {
		super();
		this.state = {
			test: null
		};
	}
	componentDidMount(){
		var video = document.querySelector("#videoElement");

		if (navigator.mediaDevices.getUserMedia) {
			navigator.mediaDevices.getUserMedia({video: true})
				.then(function(stream) {
					video.srcObject = stream;
				})
				.catch(function(err0r) {
					console.log("Something went wrong!");
				})
		}
	}

	render() {
		return (
			<video autoplay="true" id="videoElement">

			</video>);
	}
}
export default Stream;
