import React from "react";
import "./post.css";
import Content from "./content.js";

class Post extends React.Component {
	constructor() {
		super();
		this.state = {
			post: null,
			comment: null
		}
	}

	showModal = () => {
		document.querySelector("#myModal").style.display = "block";
	}

	closeModal = () => {
		document.querySelector("#myModal").style.display = "none";
	}

	handleComment = (e) => {
		this.setState({
			comment : e.target.value
		});
	}

	submitComment = () => {
		console.log(`Comment submitted : \n ${this.state.comment}`);
		//backend, add the comment to the database
		document.querySelector(".comment-entry").value = "";
	}

	render() {
		return(
			<div className="postContainer">
				<div id="myModal" className="modal">
  					<div className="modal-content">
					    <span className="close" onClick={this.closeModal}>&times;</span>
					    <p>Some text in the Modal..</p>
						<textarea className="comment-entry" onChange={this.handleComment} placeholder="Enter your comment here (Max 90 characters)" />
						<br />
						<button type="button" onClick={this.submitComment}>Submit</button>
					</div>
				</div>
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			<Content showModal={this.showModal} closeModal={this.closeModal} />
			</div>
		);
	}
}

export default Post;
