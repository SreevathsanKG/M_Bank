import Login from './components/Login'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import CustomerSignUp from './components/customer/CustomerSignup'
import CustomerDashboard from './components/customer/CustomerDashboard'
import AdminDashboard from './components/admin/AdminDashboard'
import Complaints from './components/admin/Complaints'
import ManageUser from './components/admin/ManageUser'
import AddNewUser from './components/admin/AddNewUser'
import PutUserStatus from './components/admin/PutUserStatus'
import MyAccount from './components/customer/MyAccount'
import CustomerProfile from './components/customer/CustomerProfile'
// import CustomerProfileUpdate from './components/customer/CustomerProfileUpdate'
import CreateAccount from './components/customer/CreateAccount'
import Report from './components/customer/Report'
import Transaction from './components/customer/Transaction'
import Beneficiary from './components/customer/Beneficiary'
import LoanApply from './components/customer/LoanApply'
import MyLoanApplications from './components/customer/MyLoanApplications'
import Loan from './components/customer/Loan'
import MyLoan from './components/customer/MyLoan'

function App() {

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/signup" element={<CustomerSignUp />} />
        <Route path="/customer" element={<CustomerDashboard/>}>
          <Route index element={<MyAccount/>}/>
          <Route path="account/create" element={<CreateAccount/>}/>
          <Route path="profile" element={<CustomerProfile/>}/>
          {/* <Route path="update" element={<CustomerProfileUpdate/>}/> */}
          <Route path="transaction" element={<Transaction/>} />
          <Route path="report" element={<Report/>} />
          <Route path="beneficiary" element={<Beneficiary />} />
          <Route path="loan" element={<Loan/>} />
          <Route path="loan/applications" element={<MyLoanApplications/>}/>
          <Route path="loan/apply" element={<LoanApply/>}/>
          <Route path="loan/MyLoan" element={<MyLoan/>}/>
        </Route>
        <Route path="/admin" element={<AdminDashboard/>}>
          <Route index element={<ManageUser/>}/>
          <Route path="addUser" element={<AddNewUser/>}/>
          <Route path="putUserStatus/:id/:status" element={<PutUserStatus/>}/>
          <Route path="complaints" element={<Complaints />} />
        </Route>
        {/* <Route path="/loan" element={<LoanApplicationForm/>}/> */}
      </Routes>
    </BrowserRouter>
  )
}

export default App
