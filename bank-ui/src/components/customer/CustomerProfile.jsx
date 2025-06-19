import React, { useState } from "react";
import { BreadCrumb } from "primereact/breadcrumb";
import { Button } from "primereact/button";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "primereact/resources/themes/lara-light-blue/theme.css";
import "primereact/resources/primereact.min.css";
import "primeicons/primeicons.css";

function CustomerProfile() {
    const navigate = useNavigate();
    const profile = useSelector(state => state.user); // Adjust based on how you store user

    const [userData, setUserData] = useState({
        username: profile.username || "",
        email: profile.email || "",
        phone: profile.phone || "",
        address: profile.address || ""
    });

    const [msg, setMsg] = useState("");

    const breadcrumbItems = [
        { label: "Profile", command: () => navigate("/customer/profile") }
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
            <div className="row mb-3">
                <div className="col-12 col-lg-10 offset-lg-1">
                    <BreadCrumb model={breadcrumbItems} home={home} />
                </div>
            </div>

            {/* Profile Card */}
            <div className="row justify-content-center">
                <div className="col-12 col-lg-10">
                    <div className="card custom-card">
                        <div className="card-body">
                            <div className="mb-4 text-center title-manage">
                                <h2>Profile Management</h2>
                                <p>View or update your profile details</p>
                            </div>

                            {msg && (
                                <div className="alert alert-info">{msg}</div>
                            )}

                            <form onSubmit={handleUpdate}>
                                <div className="form-group mb-3">
                                    <label>Username</label>
                                    <input
                                        type="text"
                                        name="username"
                                        className="form-control"
                                        value={userData.username}
                                        onChange={handleChange}
                                        placeholder="Enter username"
                                        autoComplete="username"
                                    />
                                </div>
                                <div className="form-group mb-3">
                                    <label>Email</label>
                                    <input
                                        type="email"
                                        name="email"
                                        className="form-control"
                                        value={userData.email}
                                        onChange={handleChange}
                                        placeholder="Enter email"
                                        autoComplete="email"
                                    />
                                </div>
                                <div className="form-group mb-3">
                                    <label>Phone</label>
                                    <input
                                        type="text"
                                        name="phone"
                                        className="form-control"
                                        value={userData.phone}
                                        onChange={handleChange}
                                        placeholder="Enter phone number"
                                        autoComplete="tel"
                                    />
                                </div>
                                <div className="form-group mb-4">
                                    <label>Address</label>
                                    <textarea
                                        name="address"
                                        className="form-control"
                                        value={userData.address}
                                        onChange={handleChange}
                                        placeholder="Enter address"
                                    />
                                </div>
                                <div className="d-flex justify-content-end">
                                    <Button
                                        label="Update Profile"
                                        icon="pi pi-refresh"
                                        className="p-button-primary"
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

export default CustomerProfile;
