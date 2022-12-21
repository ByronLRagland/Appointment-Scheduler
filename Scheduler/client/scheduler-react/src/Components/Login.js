import { useContext, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import { authenticate } from "../Services/auth";
import AuthContext from "../contexts/AuthContext";
import "./Admin/admin.css";


function Login() {

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [hasErr, setHasErr] = useState(false);

    const { login } = useContext(AuthContext);
    const history = useHistory();

    const handleUsernameChange = (evt) => setUsername(evt.target.value);
    const handlePasswordChange = (evt) => setPassword(evt.target.value);

    function handleSubmit(evt) {
        evt.preventDefault();

        const user = {
            "email": username,
            password
        };
        //console.log(user);
        authenticate(user)
            .then(u => {
                login(u);
                //console.log(user);
                history.push("/");
            })
            .catch(() => setHasErr(true));
    }

    return (
        <div className="signIn mt-5" >
        <h2 className="title-list-form mb-5">Account Login</h2 >
        <form onSubmit={handleSubmit}>
        {hasErr && <div className="alert alert-danger">
                Bad Credentials
            </div>}
            <div className="mb-2">
                <label htmlFor="username" className="form-label">Username</label>
                <input type="text" id="username" name="username" className="form-control"
                    value={username} onChange={handleUsernameChange}></input>
            </div>
            <div className="mb-2">
                <label htmlFor="password" className="form-label">Password</label>
                <input type="password" id="password" name="password" className="form-control"
                    value={password} onChange={handlePasswordChange}></input>
            </div>
            <div className="buttonContainerLogin mt-4">
                <button className="btn btn-success-new mr-2" type="submit">Login</button>
                <Link className="btn btn-primary" to="/signup">Sign Up</Link>
                {/* <Link className="btn btn-danger" to="/">Cancel</Link> */}
            
            </div>
        </form>
        </div>
    );
}

export default Login;