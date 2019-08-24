import React from "react";
import "./sidebar.css";

class Sidebar extends React.Component {
	constructor() {
		super();
		this.state = {
			photo: null
		}
	}

	render() {
		return (
			<div className="sidebar">
			<h5 className="text-secondary">Previous photos</h5>
			<br />
			<div className="photo-old">
				d
			</div>
			<span className="close">&times;</span>
			<div className="photo-old">
				d
			</div>
			<div className="photo-old">
				d
			</div>
			<div className="photo-old">
				d
			</div>
			<div className="photo-old">
				d
			</div>
			</div>
		);
	}
}

export default Sidebar;
