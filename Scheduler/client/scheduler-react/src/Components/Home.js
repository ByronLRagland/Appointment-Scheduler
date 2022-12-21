import React, { useRef, useEffect } from "react";
import { gsap } from "gsap";
import AuthContext from "../contexts/AuthContext";
import { useContext } from "react";
import { Link } from "react-router-dom";
import thumbnail from "../images/calendar-image.svg"
function Home() {
  const { user, logout } = useContext(AuthContext);
  const secondaryRef = useRef(null);
  const cardRef1 = useRef(null);
  const cardRef2 = useRef(null);
  const boxRef1 = useRef(null);
  

  useEffect(() =>{
    gsap.from(cardRef1.current, {duration: 1, autoAlpha: 0, ease: 'none', delay: 1, y:-50})
    gsap.from(cardRef2.current, {duration: 1.25, autoAlpha: 0, ease: 'none', delay: 1, y:-50})
  }, [])


  return (
    <>
      <div id="bg"></div>
      <main>
        <section ref={secondaryRef} id="secondary">
          <div id="card" ref={cardRef1}>
            <ul>
                <li>
                    <span></span>
                    <strong>All-in-one appointment management.</strong>
                </li>
                <li>
                    <span></span>
                    <strong>Everything you need to schedule anything.</strong>
                </li>
                <li>
                    <span></span>
                    <strong>Never ask "what time works for you?" again.</strong>
                </li>
            </ul>

          </div>
          <div id="card2" ref={cardRef2}>
            <img src={thumbnail} id="thumbnail-image"/>          

          </div>


        </section>

        <section id="primary">
            <h1>The scheduler project</h1>
            <p id="secondary-title">Booking appointments made easy</p>
            
            {(user) ?
                <div id="signed-up" className="signbtn">
                  <span>Welcome Back!</span>
                  <div className="light"></div>
                </div>:
                // <a href="#">Sign up here to start!</a>:

                <Link to="/signup" id="sign-up-button" className="signbtn">
                   <span>Sign up here to start!</span>
                   <div className="light"></div>
                 </Link> 
            }
            


        </section>
    </main>

      {/* <div className="container">
        <h1> Home </h1>
        <p>
          {" "}
          "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
          tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim
          veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
          commodo consequat. Duis aute irure dolor in reprehenderit in voluptate
          velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint
          occaecat cupidatat non proident, sunt in culpa qui officia deserunt
          mollit anim id est laborum."
        </p>
      </div> */}
    </>
  );
}

export default Home;
