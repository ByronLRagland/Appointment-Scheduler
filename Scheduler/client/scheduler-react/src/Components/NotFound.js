import { Link } from "react-router-dom";

function NotFound() {
  return (
    <>
      <h1>404 Not Found!</h1>
      <ul>
        <Link to="/">Home</Link>
      </ul>
      <img
        src="https://media2.giphy.com/media/Bp3dFfoqpCKFyXuSzP/200w.webp?cid=ecf05e47n2ed42atynxgpkdb6grfannjeyemk385mgdvp5p4&rid=200w.webp&ct=gf"
        alt="Short animation of Seth Meyers saying where am I"
        className="not-found"
      />
    </>
  );
}

export default NotFound;
