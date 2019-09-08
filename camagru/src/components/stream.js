import React from "react";
import Fish from "./pictures/fish.png";
import Cracks from "./pictures/cracks_burned.png";
import Hello from "./pictures/Hello.png";
import Sidebar from "./sidebar.js";
import { APIUrl, baseURL } from '../global.js';
import "./studio.css";

class Stream extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      prop: null,
      photoTaken: false,
      photoURL: null,
      user: this.props.user
    };
  }
  componentDidMount() {
    if (this.props.user)
    {
      const constraints = { video: true };

      const video = document.querySelector("video");
      const canvas = document.querySelector("canvas");
      const context = canvas.getContext("2d");
      let mediaStream = null;

      navigator.mediaDevices
        .getUserMedia(constraints)
        .then(stream => {
          video.srcObject = stream;
          video.play();
          mediaStream = stream;
        })
        .catch(err => console.error(err));

      document
        .querySelector("#snap")
        .addEventListener("click", () => snapshot(this.state.prop), false);

      function snapshot(id) {
        if (mediaStream) {
          context.drawImage(video, 0, 0, video.width, video.height);
          if (id)
            context.drawImage(
              document.querySelector(`#${id}`),
              0,
              0,
              video.width,
              video.height
            );
        }
      }

    }
  }

  componentWillReceiveProps(nextProps) {
    this.setState({
      user: nextProps.user
    });
  }

  applyProp = e => {
    if (this.state.prop !== e.target.id) {
      this.setState({
        prop: e.target.id
      });
      if (document.querySelectorAll(".selected").length > 0)
        document.querySelector(".selected").classList.remove("selected");
      document.querySelector(`#${e.target.id}`).classList.add("selected");
    } else {
      this.setState({
        prop: null
      });
      document.querySelector(`#${e.target.id}`).classList.remove("selected");
    }
  };

  postPhoto = () => {
    fetch(`${APIUrl}/posts/add`, {
      method: 'post',
      headers: {
        'Origin': baseURL,
        'Access-Control-Request-Method': 'POST',
        'Content-Type': 'application/json'
      },
      mode: 'cors',
      body: JSON.stringify({
        photo: this.state.photoURL.split(",")[1],
        userId: this.state.user.id,
        password: this.state.user.password,
        id: this.props.user.id
      })
    })
      .then(res => res.json())
      .then(data => alert(data.response))
    window.setTimeout(() => {
      this.setState({
        photoTaken: false
      });
    }, 100);
  };

  acceptUpload = () => {
    var file = document.querySelector("input[type=file]").files[0];
    var reader = new FileReader();

    reader.addEventListener(
      "load",
      () => {
        this.setState({
          photoURL: reader.result
        }, () => this.postPhoto());
      },
      false
    );

    if (file) {
      reader.readAsDataURL(file);
    }
  };

  allowPublish = () => {
    this.setState({
      photoTaken: true
    });
  };

  publish = () => {
    this.setState(
      {
        photoURL: document.querySelector("canvas").toDataURL("image/png")
      },
      () => this.postPhoto()
    );
  };

  renderPublish = () => {
    if (this.state.photoTaken) {
      return (
        <button
          onClick={this.publish}
          type="button"
          id="snap"
          className="button-primary"
        >
          Save and Publish
        </button>
      );
    }
  };

  previewOverlay = () => {
    if (this.state.prop !== null) {
      let source = document
        .querySelector(`#${this.state.prop}`)
        .getAttribute("src");
      return <img alt="preview" src={source} className="prop overlay-img" />;
    }
  };

  redirct = () =>
  {
    window.location.href = `${baseURL}/`;
  }

  render() {
    if (this.props.user == null) {
      return (
        <>
         {this.redirct()} 
        </>
      );
    }
    return (
      <>
        <Sidebar user={this.state.user} />
        <div className="container-studio">
          <h1 className="text-secondary">Welcome to the studio!</h1>
          <div className="container-props">
            <img
              id="Fish"
              onClick={this.applyProp}
              className="prop"
              src={Fish}
              alt="Fish"
            />
            <img
              onClick={this.applyProp}
              id="Cracks"
              className="prop"
              src={Cracks}
              alt="Cracks"
            />
            <img
              onClick={this.applyProp}
              id="Hello"
              className="prop"
              src={Hello}
              alt="Hello"
            />
          </div>
          <div className="prop camera">
            <video className="main" width="320" height="240"></video>
            <video width="100%"></video>
            {this.previewOverlay()}
          </div>
          <br />
          <button
            type="button"
            onClick={this.allowPublish}
            id="snap"
            className="button-primary"
          >
            Take Photo
          </button>
          <br />
          <p>or upload here:</p>
          <input type="file" onChange={this.acceptUpload} />
          <br />
          <canvas width="320" height="240"></canvas>
          <br />
          {this.renderPublish()}
        </div>
      </>
    );
  }
}
export default Stream;
