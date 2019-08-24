import React from "react";
import "./header.css";
import home from "./pictures/home.png";
import settings from "./pictures/account.png";
import camera from "./pictures/camera.png";

const Header = props => {
  if (props.user) {
    return (
      <div className="container-head">
        <div className="container-icon" >
          <img src={home} className="icon" alt="home" />
        </div>
        <div className="container-icon small-top-margin">
          <img src={settings} className="icon" alt="home" />
        </div>
        <div className="container-icon top-margin">
          <img src={camera} className="icon" alt="home" />
        </div>
        <h1
          onClick={props.showme}
          className="text-primary text-header text-center"
        >
          Camagru.
        </h1>
      </div>
    );
  } else {
    return (
      <div className="container-head">
        <div className="container-icon">
          <img src={home} className="icon" alt="home" />
        </div>
        <div className="container-icon small-top-margin">
          <img src={settings} className="icon" alt="home" />
        </div>
        <h1
          onClick={props.showme}
          className="text-primary text-header text-center"
        >
          Camagru.
        </h1>
      </div>
    );
  }
};

export default Header;
