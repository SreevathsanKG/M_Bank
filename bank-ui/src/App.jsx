import { useState } from 'react'
import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import CustomerSignUp from './components/customer/CustomerSignup'
import CustomerDashboard from './components/customer/CustomerDashboard'
import HDB from './components/customer/CustomerDashboard'
import LoanApplicationForm from './components/customer/LoanApplicationForm'
import LoanStepper from './components/customer/LoanStepper'
import AdminDashboard from './components/admin/AdminDashboard'
import Complaints from './components/admin/Complaints'
import ManageUser from './components/admin/ManageUser'
import AddNewUser from './components/admin/AddNewUser'
import PutUserStatus from './components/admin/PutUserStatus'
import MyAccount from './components/customer/MyAccount'
import CustomerProfile from './components/customer/CustomerProfile'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<CustomerSignUp />} />
        <Route path="/customer" element={<CustomerDashboard/>}>
          <Route index element={<MyAccount/>}/>
          <Route path="profile" element={<CustomerProfile/>}/>
        </Route>
        <Route path="/admin" element={<AdminDashboard/>}>
          <Route index element={<ManageUser/>}/>
          <Route path="addUser" element={<AddNewUser/>}/>
          <Route path="putUserStatus/:id/:status" element={<PutUserStatus/>}/>
          <Route path="complaints" element={<Complaints />} />
        </Route>
        {/* <Route path="/loan" element={<LoanApplicationForm/>}/> */}
        {/* <Route path="/stepper" element={<CustomerProfile/>}/> */}
        Route
      </Routes>
    </BrowserRouter>
  )
}

export default App
