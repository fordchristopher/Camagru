import React from "react";
import "./post.css";
import Content from "./content.js";
import { APIUrl, baseURL } from "../global";

class Post extends React.Component {
	constructor() {
		super();
		this.state = {
			posts: null,
			posts_loaded: false,
			comments_loaded: false,
			comments: null,
			postId: -1,
			comment: "",
			pag: 0
		}
		this.likePost = this.likePost.bind(this);
		this.setCommentId = this.setCommentId.bind(this);
		this.setPostId = this.setPostId.bind(this);
	}

	componentDidMount() {
		this.getPosts();
	}

	getPosts = () => {
		fetch(`${APIUrl}/posts/getAll?pag=${this.state.pag}`)
		.then(response => response.json())
		.then(data => {
			this.setState({
				posts: data,
				posts_loaded: true
			});
		})
	}

	setCommentId = (id) => {
		this.setState({
			commentId: id
		})
	}

	showModal = (postId) => {
		this.setPostId(postId);
		document.querySelector("#myModal").style.display = "block";
		fetch(`${APIUrl}/posts/getComments?postId=${postId}`)
		.then(response => response.json())
		.then(data => {
			this.setState({
				comments_loaded: true,
				comments: data
			})
		});
	}

	closeModal = () => {
		document.querySelector("#myModal").style.display = "none";
	}

	handleComment = (e) => {
		this.setState({
			comment : e.target.value
		});
	}

	setPostId = (id) => {
		this.setState({
			postId: id
		})
	}

	submitComment = () => {
		fetch(`${APIUrl}/posts/addComment`, {
			method: 'post',
			headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				postId: this.state.postId,
				userId: this.props.user.id,
				content: this.state.comment,
				password: this.props.user.password
			})
		})
		.then(response => response.json())
		.then(data => alert(data.response))
		document.querySelector(".comment-entry").value = "";
		window.location.reload();
	}

	likePost = (postId, userId) => {
		fetch(`${APIUrl}/posts/likePost`, {
			method: 'post',
			headers: {
				'Origin': baseURL,
				'Access-Control-Request-Method': 'POST',
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				postId: postId,
				userId: userId
			})
		})
		.then(response => response.json())
		.then(data => {
			alert(data.response);
			if (data.response === "Post liked!")
				window.location.reload();
		});
		
	}

	renderPosts = () => {
		
		if (this.state.posts.length > 0)
		{
		return (
			this.state.posts.map(post => 
				<Content
				showModal={this.showModal}
				closeModal={this.closeModal}
				likePost={this.likePost}
				user={this.props.user}
				postData={post}
				setCommentId={this.setCommentId}
				key={post.id}
				/>
			)
		);
			} else {
				return (
					<p>No more posts! Check back later</p>
				);
			}
	}

	incrementPag = () => {
		this.setState({
			pag: this.state.pag + 1
		}, () => {
			this.getPosts();
		});
	}

	renderPagination = () => {
		if (this.state.pag === 0)
		{
			return (
				<button type="button" onClick={this.incrementPag}>Enable Pagination</button>
			)
		} else {
			return (
				<button type="button" onClick={this.incrementPag}>Next Page</button>
			)
		}
	}

	render() {
		if (this.state.posts_loaded)
		{
			return (
				<div className="postContainer">
					{this.renderPagination()}
					<div id="myModal" className="modal">
						<div className="modal-content">
							<span className="close" onClick={this.closeModal}>&times;</span>
							{
								this.state.comments !== null && this.state.comments.length > 0 &&
								this.state.comments.map(comment => <div key={comment.id}><h4>{comment.username} at <br /> {comment.date.split(".")[0].replace("T", " ")}</h4><p>{comment.content}</p> <hr /></div>)
							}
							{
								this.props.user !== null &&
								<>
									<textarea className="comment-entry" onChange={this.handleComment} placeholder="Enter your comment here (Max 90 characters)" />
									<br />
									<button type="button" onClick={this.submitComment}>Submit</button>
								</>
							}
						</div>
					</div>
					{this.renderPosts()}
					{this.renderPagination()}
				</div>
			);
		}
		else {
			return (
				<div>
					loading
				</div>
			)
		}
	}
}

export default Post;
