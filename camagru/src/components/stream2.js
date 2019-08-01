import React from "react";

class Stream extends React.Component {
	constructor() {
		super();
		this.state = {
			videoSrc: null
		};
	}
		componentDidMount(){
			navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mozGetUserMedia || navigator.msGetUserMedia || navigator.oGetUserMedia;
			if (navigator.getUserMedia) {
				navigator.getUserMedia({video: true}, this.handleVideo, this.videoError);
			}
		}
		handleVideo = (stream) => {
			// Update the state, triggering the component to re-render with the correct stream
			//this.setState({ videoSrc: window.URL.createObjectURL(stream) });
			this.setState({ videoSrc:stream });
			this.state.videoSrc.play();
		}
		videoError = () => {
			alert("Error with da video");
		}
		render() {
			return (
				<div>
					<video src={this.state.videoSrc} autoPlay="true" />
				</div>)
			;
		}
}

export default Stream;
