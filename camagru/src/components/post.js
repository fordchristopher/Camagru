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
		this.likePost = this.likePost.bind(this);
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

	likePost = (postId, userId) => {

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
				<Content
					showModal={this.showModal}
					closeModal={this.closeModal}
					likePost={this.likePost}
					user={this.props.user}
					//Need to pass in posts from a map function.  I was wokring on the like button functionality
					//But i am thinking that it will be easier once I render out the posts
				/>
			</div>
		);
	}
}

export default Post;
