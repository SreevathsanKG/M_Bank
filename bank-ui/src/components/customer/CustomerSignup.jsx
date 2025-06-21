import { Link, useNavigate } from 'react-router-dom'
import { useEffect, useState } from 'react'
import axios from 'axios'
import '../../../src/css/CustomerSignup.css'

function AdminAddUser() {
    const [firstName, setFirstName] = useState("")
    const [lastName, setLastName] = useState("")
    const [birthday, setBirthday] = useState("")
    const [gender, setGender] = useState("")
    const [email, setEmail] = useState("")
    const [phone, setPhone] = useState("")
    const [address, setAddress] = useState("")
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [role, setRole] = useState("")
    const [branchId, setBranchId] = useState("")
    const [branches, setBranches] = useState([])
    const [msg, setMsg] = useState("")

    const navigate = useNavigate()

    useEffect(() => {
        if (role === "EXECUTIVE") {
            axios.get("http://localhost:8080/api/branch/get-all", {
                headers: { Authorization: "Bearer " + localStorage.getItem("token") }
            }).then((res) => setBranches(res.data))
              .catch((err) => console.error("Error fetching branches", err))
        }
    }, [role])

    const handleSubmit = async (e) => {
        e.preventDefault()

        const commonPayload = {
            firstName, lastName, gender, dateOfBirth: birthday,
            email, phoneNumber: phone, address, username, password
        }

        try {
            if (role === "MANAGER") {
                await axios.post("http://localhost:8080/api/manager/post", commonPayload, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                })
            } else if (role === "EXECUTIVE") {
                if (!branchId) return alert("Please select a branch")
                await axios.post(`http://localhost:8080/api/executive/post/${branchId}`, commonPayload, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                })
            } else {
                await axios.post("http://localhost:8080/api/customer/post", commonPayload, {
                    headers: { Authorization: "Bearer " + localStorage.getItem("token") }
                })
            }

            setMsg("User registered successfully!")
        } catch (error) {
            console.error(error)
            setMsg("Something went wrong.")
        }
    }

    return (
        <div className="signup-container min-vh-100 d-flex align-items-center">
            <div className="card signup-card shadow-p4">
                <h2 className="mb-4 text-center signup-title fw-bold">Register New User</h2>
                <form className="row g-3" onSubmit={handleSubmit}>
                    {msg && <div className="alert alert-info">{msg}</div>}

                    <div className="col-md-6 form-floating">
                        <input type="text" className="form-control" placeholder="First Name" onChange={(e) => setFirstName(e.target.value)} />
                        <label>First Name</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="text" className="form-control" placeholder="Last Name" onChange={(e) => setLastName(e.target.value)} />
                        <label>Last Name</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="date" className="form-control" placeholder="Birthday" onChange={(e) => setBirthday(e.target.value)} />
                        <label>Birthday</label>
                    </div>
                    <div className="col-md-6 d-flex align-items-center">
                        <label className='me-3'>Gender:</label>
                        {["MALE", "FEMALE", "OTHER"].map(g => (
                            <div key={g} className="form-check me-3">
                                <input className="form-check-input" type="radio" name="gender" value={g} onChange={(e) => setGender(e.target.value)} />
                                <label className="form-check-label">{g[0] + g.slice(1).toLowerCase()}</label>
                            </div>
                        ))}
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="email" className="form-control" placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
                        <label>Email</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="tel" className="form-control" placeholder="Phone" onChange={(e) => setPhone(e.target.value)} />
                        <label>Phone</label>
                    </div>
                    <div className="col-md-12 form-floating">
                        <input type="text" className="form-control" placeholder="Address" onChange={(e) => setAddress(e.target.value)} />
                        <label>Address</label>
                    </div>
                    <div className="col-md-6 form-floating">
                        <input type="text" className="form-control" placeholder="Username" onChange={(e) => setUsername(e.target.value)} />
                        <label>Username</label>
                    </div>
                    <div className="col-md-6 form-floating mb-2">
                        <input type="password" className="form-control" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
                        <label>Password</label>
                    </div>
                    <div className="col-md-12 form-floating">
                        <select className="form-select" onChange={(e) => setRole(e.target.value)}>
                            <option value="">-- Select Role --</option>
                            <option value="CUSTOMER">Customer</option>
                            <option value="MANAGER">Manager</option>
                            <option value="EXECUTIVE">Executive</option>
                        </select>
                        <label>Role</label>
                    </div>

                    {role === "EXECUTIVE" && (
                        <div className="col-md-12 form-floating">
                            <select className="form-select" onChange={(e) => setBranchId(e.target.value)}>
                                <option value="">-- Select Branch --</option>
                                {branches.map((b) => (
                                    <option key={b.id} value={b.id}>
                                        {b.branchName} ({b.ifscCode})
                                    </option>
                                ))}
                            </select>
                            <label>Select Branch (For Executive)</label>
                        </div>
                    )}

                    <div className="d-grid gap-2 mt-2">
                        <button className="btn btn-primary rounded-pill" type="submit">Register</button>
                    </div>
                    <div className="text-center">
                        Back to Admin?{" "}
                        <Link to="/admin">Dashboard</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AdminAddUser;
