import React from "react";
import './content.css';

const Content = (props) => {
	return (
		<div className='card-front'>
			<div className="container-photo">
			</div>
			<div className='container-post'>
				<h1 className="text-primary">Photo by User</h1>
				<h4 className="text-primary">Posted at time</h4>
				<button type="button" onClick={props.showModal}>Comments (5)</button>
				<button type="button">Like (10)</button>
			</div>
		</div>
	);
}

export default Content;
