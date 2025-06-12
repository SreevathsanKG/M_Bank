import { useState } from "react"
import axios from "axios"

function Login() {

    let [username, setUsername] = useState("")
    let [password, setPassword] = useState("")
    let [msg, setMsg] = useState("")

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
        <div className="container">
            <div className="row">
                <div className="col-md-12">
                    <br /><br /><br /><br />
                </div>
            </div>
            <div className="row">
                <div className="col-md-3"></div>
                <div className="col-md-5">
                    <div className="card">
                        <div className="card-header">LOGIN</div>
                        <div className="card-body">
                            {
                                msg!=""?<div>
                                    <div className="alert alert-info">{msg}</div>
                                </div>:""
                            }
                            <div className="mb-2">
                                <label>Enter Username: </label>
                                <input type="text" onChange={($e) => setUsername($e.target.value)} className="form-control"/>
                            </div>
                            <div className="mb-2">
                                <label>Enter Password: </label>
                                <input type="text" onChange={($e) => setPassword($e.target.value)} className="form-control"/>
                            </div>
                            <div className="mb-2">
                                <button className="btn btn-primary" onClick={() => processLogin()}>LOGIN</button>
                            </div>
                        </div>
                        <div className="card-footer">
                            Don't have an account? Sign Up here.
                        </div>
                    </div>
                </div>
                <div className="col-md-3"></div>
            </div>
        </div>
    )
}

export default Login