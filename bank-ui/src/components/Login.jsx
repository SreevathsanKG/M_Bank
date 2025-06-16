import { useState } from "react"
import axios from "axios"
import { Link } from "react-router-dom"
import { Eye, EyeSlash } from 'react-bootstrap-icons'
import './../css/login.css'


function Login() {

    let [username, setUsername] = useState("")
    let [password, setPassword] = useState("")
    let [msg, setMsg] = useState("")
    const [showPassword, setShowPassword] = useState(false);

    const processLogin = async () => {
        let encodedString = window.btoa(username+":"+password)
        try{
            const response = await axios.get("http://localhost:8080/api/user/token",{
                headers : { "Authorization":"Basic "+encodedString}
            })
            // console.log(response.data.token)
            let token = response.data.token
            localStorage.setItem("token", token)

            let details = await axios.get("http://localhost:8080/api/user/details",{
                headers: {"Authorization":"Bearer "+token}
            })
            // console.log(details)
            let role = details.data.user.role
            switch (role) {
                case "CUSTOMER":
                    console.log("go to customer dashboard")
                    break;
                case "CUSTOMER_EXECUTIVE":
                    console.log("go to customer_executive dashboard")
                    break;
                default:
                    setMsg("Login Disabled!.. Contact admin at admin@example.com")
                    break;
            }
            setMsg("Login Seccess!!")
        }catch(err){
            setMsg("Invalid Credentials!!")
        }
    }

    return (
        <div className="container-fluid min-vh-100 d-flex  align-items-center" >
            <div className="row w-100">

                <div className="col-md-6 d-flex flex-column justify-content-center px-5">
                    <h1 className="display-5 text-white fw-bold mb-3">Welcome to Maveric Bank</h1>
                    <p className="lead text-white text-shadow-md">
                        Manage your finances with ease. Secure, reliable, and accessible banking at your fingertips.
                        Log in to check your account, transfer funds, apply for loans, and more!
                    </p>
                    <p className="text-white mt-3">
                        Need help? Contact support@maverickbank.com
                    </p>
                </div>

                <div className="col-md-6 d-flex justify-content-center">
                    <div className="card shadow-sm w-75 p-4">
                        <div className="card-body">
                            <div className="text-center mb-4">
                                <img src=".\images\logo-transparent.png" className="login-logo" />
                                <h3 className="card-title gradient-text">LOGIN</h3>
                            </div>
                            {
                                msg!=""?<div>
                                    <div className="alert alert-info">{msg}</div>
                                </div>:""
                            }
                            <div class="form-floating mb-3">
                                <input type="email" class="form-control" placeholder="name@example.com"
                                    onChange={(e) => setUsername(e.target.value)} />
                                <label>Email address</label>
                            </div>
                            <div class="form-floating">
                                <input type={showPassword ? "text" : "password"} class="form-control" placeholder="Password"
                                    onChange={(e) => setPassword(e.target.value)} />
                                <label>Password</label>
                                <span
                                    onClick={() => setShowPassword(!showPassword)}
                                    style={{
                                        position: "absolute",
                                        right: "10px",
                                        top: "50%",
                                        transform: "translateY(-50%)",
                                        cursor: "pointer",
                                        zIndex: 2,
                                    }}
                                >
                                    {showPassword ? <EyeSlash /> : <Eye />}
                                </span>
                            </div>
                            <div class="d-grid gap-2 mt-3">
                                <button class="btn btn-primary rounded-pill" onClick={() => processLogin()}>LOGIN</button>
                            </div>
                            <div className="text-center mt-3">
                                Don't have an account?{" "}
                                <Link to="/register">Sign Up</Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Login