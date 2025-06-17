import { useState } from 'react'
import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import CustomerSignUp from './components/customer/CustomerSignup'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<CustomerSignUp />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
