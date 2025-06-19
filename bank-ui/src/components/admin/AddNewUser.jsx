import React, { useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { useNavigate } from "react-router-dom";
import { Button } from "primereact/button";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";
import "../../css/AdminDashboard.css";
import axios from "axios";

function AddNewUser() {
    const navigate = useNavigate();

    const breadcrumbItems = [
        { label: 'Manage User', command: () => navigate('/admin') },
        { label: 'Add New User' }
    ];

    const home = { icon: 'pi pi-home', command: () => navigate('/admin') };

    let [msg, setMsg] = useState("")
    let [username, setUsername] = useState("")
    let [password, setPassword] = useState("")
    let [role, setRole] = useState("")

    const postUser = async (e) => {
        e.preventDefault()
        try {
            await axios.post("http://localhost:8080/api/user/signup", {
                "username": username,
                "password": password,
                "role": role
            },{
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token')
            }
        })
            setMsg("User created successfully")
        } catch (error) {
            setMsg("Something went wrong")
        }
    }

    return (
        <div className="container-fluid py-4">
            {/* Breadcrumb outside the card */}
            <div className="row mb-3">
                <div className="col-12 col-lg-10 offset-lg-1">
                    <BreadCrumb model={breadcrumbItems} home={home} />
                </div>
            </div>

            {/* Add User Card */}
            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    <div className="card custom-card">
                        <div className="card-body">
                            <div className="mb-4 text-center title-manage">
                                <h2>Add New User</h2>
                                <p>Fill in the user details to register a new account</p>
                            </div>
                            <div>
                                {
                                    msg!=""?<div>
                                        <div className="alert alert-info">{msg}</div>
                                    </div>:""
                                }
                            </div>

                            <form onSubmit={postUser}>
                                <div className="form-group mb-3">
                                    <label>Username</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        name="username"
                                        onChange={(e) => setUsername(e.target.value)}
                                        placeholder="Enter username"
                                        autoComplete="username"
                                    />
                                </div>
                                <div className="form-group mb-3">
                                    <label>Password</label>
                                    <input
                                        type="password"
                                        className="form-control"
                                        name="password"
                                        onChange={(e) => setPassword(e.target.value)}
                                        placeholder="Enter password"
                                        autoComplete="new-password"
                                    />
                                </div>
                                <div className="form-group mb-4">
                                    <label>Role</label>
                                    <select
                                        name="role"
                                        className="form-control"
                                        onChange={(e) => setRole(e.target.value)}
                                    >
                                        <option value="">-- Select Role --</option>
                                        <option value="CUSTOMER">CUSTOMER</option>
                                        <option value="EXECUTIVE">EXECUTIVE</option>
                                        <option value="MANAGER">MANAGER</option>
                                        <option value="ADMIN">ADMIN</option>
                                    </select>
                                </div>

                                <div className="d-flex justify-content-between">
                                    <Button
                                        label="Cancel"
                                        icon="pi pi-times"
                                        className="p-button-secondary"
                                        type="button"
                                        onClick={() => navigate('/admin')}
                                    />
                                    <Button
                                        label="Create User"
                                        icon="pi pi-check"
                                        className="p-button-success"
                                        type="submit"
                                    />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AddNewUser;