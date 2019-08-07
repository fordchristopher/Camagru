import React from "react";
import './content.css';

const Content = (props) => {
	return (
		<div className='card-front'>
			<div className="container-photo">
			</div>
			<div className='container-post'>
				<h1 className="text-primary">Title</h1>
				<h4 className="text-primary">Posted by user at time</h4>
				<p className="text-secondary post-margin">He is currently the founder of Dvorak Media. Previously, Andrey was a product designer at </p>
				<button type="button" onClick={props.showModal}>Comments (5)</button>
				<button type="button">Like (10)</button>
			</div>
		</div>
	);
}

export default Content;
