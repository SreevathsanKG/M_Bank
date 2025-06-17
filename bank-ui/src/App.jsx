import { useState } from 'react'
import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import CustomerSignUp from './components/customer/CustomerSignup'
import CustomerDashboard from './components/customer/CustomerDashboard'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<CustomerSignUp />} />
        <Route path="/customer" element={<CustomerDashboard/>} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
