import React, { useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

function CustomerProfileUpdate() {
    const navigate = useNavigate();
    const profile = useSelector(state => state.user); // Adjust based on how you store user

    let [firstName, setFirstName] = useState("")
    let [lastName, setLastName] = useState("")
    let [birthday, setBirthday] = useState("")
    let [gender, setGender] = useState("")
    let [email, setEmail] = useState("")
    let [phone, setPhone] = useState("")
    let [address, setAddress] = useState("")

    const [msg, setMsg] = useState("");

    const breadcrumbItems = [
        { label: "Profile", command: () => navigate("/customer/profile") },
        { label: "Update", command: () => navigate("/customer/update") }
    ];
    const home = { icon: "pi pi-home", command: () => navigate("/customer") };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setUserData((prev) => ({
            ...prev,
            [name]: value
        }));
    };

    const handleUpdate = async (e) => {
        e.preventDefault();
        try {
            await axios.put("http://localhost:8080/api/user/update", {
                ...Object.fromEntries(
                    Object.entries(userData).map(([k, v]) => [k, v === "" ? null : v])
                )
            }, {
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                }
            });
            setMsg("Profile updated successfully.");
        } catch (error) {
            console.error(error);
            setMsg("Failed to update profile.");
        }
    };

    return (
        <div className="container-fluid py-4">
            {/* Breadcrumb */}
            <div className="row mb-1">
                <div className="col-12 col-lg-10 offset-lg-1">
                    <BreadCrumb model={breadcrumbItems} home={home} />
                </div>
            </div>

            {/* Profile Card */}
            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    <div className="card custom-card">
                        <div className="card-body">
                            <div className="text-center title-manage">
                                <h2>Profile Update</h2>
                            </div>

                            {msg && (
                                <div className="alert alert-info">{msg}</div>
                            )}

                            <form className="row g-3" onSubmit={handleUpdate}>
                    {
                        msg != "" ? <div>
                            <div className="alert alert-info">{msg}</div>
                        </div> : ""
                    }
                    <div className="col-md-6 form-floating">
                        <input type="text" className="form-control" placeholder="First Name"
                            onChange={(e) => setFirstName(e.target.value)}/>
                        <label>First Name</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="text" className="form-control" placeholder="Second Name" 
                            onChange={(e) => setLastName(e.target.value)}/>
                        <label>Second Name</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="date" className="form-control" placeholder="Birthday" 
                            onChange={(e) => setBirthday(e.target.value)}/>
                        <label>Birthday</label>
                    </div>
                    <div className="col-md-6 d-flex align-items-center">
                        <label className='me-3'>Gender:</label>
                        <div className='form-check me-2'>
                            <input className="form-check-input" type="radio" name="gender" value={"MALE"}
                                onChange={(e) => setGender(e.target.value)}/>
                            <label className="form-check-label" >Male</label>
                        </div>
                        <div className='form-check me-2'>
                            <input className="form-check-input" type="radio" name="gender" value={"FEMALE"}
                                onChange={(e) => setGender(e.target.value)}/>
                            <label className="form-check-label" >Female</label>
                        </div>
                        <div className='form-check'>
                            <input className="form-check-input" type="radio" name="gender" value={"OTHER"}
                                onChange={(e) => setGender(e.target.value)}/>
                            <label className="form-check-label" >Other</label>
                        </div>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="email" className="form-control" placeholder="Email" 
                            onChange={(e) => setEmail(e.target.value)}/>
                        <label>Email</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="tel" className="form-control" placeholder="Phone Number" 
                            onChange={(e) => setPhone(e.target.value)}/>
                        <label>Phone Number</label>
                    </div>
                    <div className="col-md-12 form-floating">
                        <input type="text" className="form-control" placeholder="Address" 
                            onChange={(e) => setAddress(e.target.value)}/>
                        <label>Address</label>
                    </div>
                    <div className="col-md-6 form-floating ">
                        <input type="text" className="form-control" placeholder="Username" 
                            onChange={(e) => setUsername(e.target.value)}/>
                        <label>Username</label>
                    </div>
                    <div className="col-md-6 form-floating mb-4">
                        <input type="text" className="form-control" placeholder="Password" 
                            onChange={(e) => setPassword(e.target.value)}/>
                        <label>Password</label>
                    </div>
                    {/* <div className="col-md-6">
                        <label >Aadhar:</label>
                        <input type="file" className="form-control" id="inputGroupFile02" 
                            onChange={(e) => setAadhar(e.target.value)}/>
                    </div>
                    <div className="col-md-6">
                        <label >Pan:</label>
                        <input type="file" className="form-control" id="inputGroupFile02" 
                            onChange={(e) => setPan(e.target.value)}/>
                    </div> */}
                    <div className="d-grid gap-2">
                        <button className="login-btn btn-primary rounded-pill" type='submit'>SIGN UP</button>
                    </div>
                    <div className="text-center">
                        Already have an account?{" "}
                        <Link onClick={() => navigate("/")} to="/">Login</Link>
                    </div>
                </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default CustomerProfileUpdate
