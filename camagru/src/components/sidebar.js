import React from "react";
import "./sidebar.css";
import { APIUrl, baseURL } from "../global.js";

class Sidebar extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
			photos: null,
			photosLoaded: false,
			user: props.user,
			page: 1
		}
	}
	componentDidMount() {
		this.getPhotos();
	}

	getPhotos = () => {
		if (this.state.user) {
			fetch(`${APIUrl}/posts/getAllByUserId?userId=${this.state.user.id}&page=${this.state.page}`)
				.then(response => response.json())
				.then(data => {
					this.setState({
						photos: data,
						photosLoaded: true
					});
				})
		}
	}

	componentWillReceiveProps(nextProps) {
		this.setState({
			user: nextProps.user
		}, () => {
			this.getPhotos();
	});
	}

	deletePhoto = (photoId) => {
		fetch(`${APIUrl}/posts/deletePost`, {
			method: 'post',
   			headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
 		    },
      		body: JSON.stringify({
				userId: this.state.user.id,
      	 		email: this.state.user.email,
				password: this.state.user.password,
				salt: this.state.user.salt,
				postId: photoId
			  })
			}
		).then(() => this.getPhotos()
		)
	}

	renderPhotos = () => {
		if (this.state.photos.length > 0) {
		return (
		this.state.photos.map(photo => {
			return (
			<div key={photo.id}>
				<span onClick= {() => this.deletePhoto(photo.id)} className="close">&times;</span>
				<div className="photo-old">
					<img className="thumb" src={photo.url} alt={photo.id}/>
				</div>	
			</div>
			);
			})
		);
		} else {
			return (
				<p>
					No more previous photos!
				</p>
			)
		}
	}

	nextPage = (e) => {
		this.setState({
			page: this.state.page + 1
		}, () => this.getPhotos());
	}

	renderNext = () => {
		if (this.state.photos && this.state.photos.length > 0) {
			return (<button className="paginate" onClick={() => this.nextPage()}>Next</button>);
		}
	}

	render() {
		return (
			<div className="sidebar">
				<h5 className="text-secondary">Previous photos</h5>
				<br />
				{
					this.state.photosLoaded === true &&
					this.renderPhotos()
				}
				{
					this.renderNext()
				}
				</div>
		);
	}
}

export default Sidebar;
