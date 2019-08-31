import React from "react";
import './content.css';
import { APIUrl } from "../global";

const Content = (props) => {
	return (
		<div className='card-front'>
			<div className="container-photo">
				<img src={props.postData.url} alt={props.postData.id} />
			</div>
			<div className='container-post'>
				<h1 className="text-primary">Photo by {props.postData.username}</h1>
				<h4 className="text-primary">Posted at {props.postData.timestamp.split(".")[0].replace("T", " ")}</h4>
				{props.user == null &&
				<>
					<button type="button" onClick={() => props.showModal(props.postData.id)}>Comments ({props.postData.commentCount})</button>
					<button type="button">Like ({props.postData.likesCount})</button>
				</>
				}
				{props.user &&
				<>
					<button type="button" onClick={() => props.showModal(props.postData.id)}>Comments ({props.postData.commentCount})</button>
					<button onClick={props.likePost(props.postData.id, props.postData.user.id)} type="button">Like ({props.postData.likesCount})</button>
				</>
				}
			</div>
		</div>
	);
}

export default Content;
