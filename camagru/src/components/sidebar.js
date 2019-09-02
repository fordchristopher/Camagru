import React from "react";
import "./sidebar.css";
import { APIUrl } from "../global.js";

class Sidebar extends React.Component {
	constructor() {
		super();
		this.state = {
			photos: null,
			photosLoaded: false
		}
	}
	componentDidMount() {
		if (this.props.user) {
			fetch(`${APIUrl}/posts/getAllByUserId?userId=${this.props.user.id}`)
				.then(response => response.json())
				.then(data => {
					this.setState({
						photos: data,
						photosLoaded: true
					})
				})
		}
	}

	render() {
		return (
			<div className="sidebar">
				<h5 className="text-secondary">Previous photos</h5>
				<br />
				{this.state.photosLoaded &&
				this.state.photos.map(photo => {
					<div>
						<span className="close">&times;</span>
						<div className="photo-old">
							{photo.id}
						</div>	
					</div>
					})
				}
			</div>
		);
	}
}

export default Sidebar;
