import React, { useState } from 'react';
// import 'bootstrap/dist/css/bootstrap.min.css'; // Ensure Bootstrap CSS is imported
import '../../css/LoanForm.css'; // Custom CSS for this component

function LoanApplicationForm() {
    const [currentStep, setCurrentStep] = useState(1); // State to manage current step (1, 2, or 3)

    // State for Loan Details (Step 1)
    const [selectedLoanType, setSelectedLoanType] = useState('personal');
    const [financingType, setFinancingType] = useState('');
    const [bankService, setBankService] = useState('');
    const [loanAmount, setLoanAmount] = useState('');
    const [loanDuration, setLoanDuration] = useState('');

    // State for Personal Details (Step 2 - add more as needed)
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [birthday, setBirthday] = useState('');
    const [gender, setGender] = useState('');

    // State for Documents Upload (Step 3 - add more as needed)
    const [document1, setDocument1] = useState(null);
    const [document2, setDocument2] = useState(null);

    const handleNext = () => {
        // You would typically add validation here before moving to the next step
        setCurrentStep(prevStep => prevStep + 1);
    };

    const handleSubmit = () => {
        // This function will be called on the final step
        console.log('Form Submitted!');
        console.log('Loan Details:', { selectedLoanType, financingType, bankService, loanAmount, loanDuration });
        console.log('Personal Details:', { firstName, lastName, birthday, gender });
        console.log('Documents:', { document1, document2 });
        // Here you would send data to your backend API
        alert('Loan Application Submitted Successfully!'); // Use a custom modal in a real app
    };

    // Helper for rendering step content
    const renderStepContent = () => {
        switch (currentStep) {
            case 1:
                return (
                    <div className="loan-form-section">
                        <h4 className="mb-3 text-secondary">Choose your loan type</h4>
                        <div className="d-flex flex-wrap gap-3 mb-4">
                            {/* Personal Loan */}
                            <div
                                className={`loan-type-box d-flex flex-column align-items-center justify-content-center p-3 rounded-3 cursor-pointer ${selectedLoanType === 'personal' ? 'selected' : ''}`}
                                onClick={() => setSelectedLoanType('personal')}
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" className="bi bi-person-fill mb-2" viewBox="0 0 16 16">
                                    <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                                </svg>
                                <span className="fw-bold">Personal Loan</span>
                            </div>
                            {/* Home Loan */}
                            <div
                                className={`loan-type-box d-flex flex-column align-items-center justify-content-center p-3 rounded-3 cursor-pointer ${selectedLoanType === 'home' ? 'selected' : ''}`}
                                onClick={() => setSelectedLoanType('home')}
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" className="bi bi-house-door-fill mb-2" viewBox="0 0 16 16">
                                    <path d="M6.5 14.5v-3.505c0-.245.25-.495.5-.495h2c.25 0 .5.25.5.5v3.505h4V7.247l-6-5.48-6 5.48V14.5z"/>
                                    <path d="M2.037 12.977l-.008.008a.5.5 0 0 1-.013.013l-.007.007a.5.5 0 0 1-.013.013L0 12.569V14.5a.5.5 0 0 0 .5.5h.5a.5.5 0 0 0 .5-.5v-1.923l2.854-2.608a.5.5 0 0 1 .692 0L14 12.569V14.5a.5.5 0 0 0 .5.5h.5a.5.5 0 0 0 .5-.5v-1.923l-2.854-2.608a.5.5 0 0 1-.692 0L2.037 12.977zM9.5 12.569l-3.354-3.056a.5.5 0 0 1 .692 0L8 10.94l3.162-2.885a.5.5 0 0 1 .692 0L15 12.569V7.247L8 1.767 1 7.247V12.569z"/>
                                </svg>
                                <span className="fw-bold">Home Loan</span>
                            </div>
                            {/* Business Loan */}
                            <div
                                className={`loan-type-box d-flex flex-column align-items-center justify-content-center p-3 rounded-3 cursor-pointer ${selectedLoanType === 'business' ? 'selected' : ''}`}
                                onClick={() => setSelectedLoanType('business')}
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" className="bi bi-briefcase-fill mb-2" viewBox="0 0 16 16">
                                    <path d="M6.5 1A1.5 1.5 0 0 0 5 2.5V3H1.5A1.5 1.5 0 0 0 0 4.5v1.384l7.614 2.032c.579.155 1.158 0 1.737-.155L16 5.884V4.5A1.5 1.5 0 0 0 14.5 3H11v-.5A1.5 1.5 0 0 0 9.5 1zm0 1h3a.5.5 0 0 1 .5.5V3H6v-.5a.5.5 0 0 1 .5-.5"/>
                                    <path d="M.5 9.616V14.5A1.5 1.5 0 0 0 2 16h12a1.5 1.5 0 0 0 1.5-1.5V9.616l-1.394-.372a.5.5 0 0 0-.61.353L13 10.5h-2.294a.5.5 0 0 0-.487.39l-1.01 4A.5.5 0 0 1 8.01 15h-.02a.5.5 0 0 1-.497-.39l-1.01-4a.5.5 0 0 0-.487-.39H3l-.012-.004a.5.5 0 0 0-.61-.353z"/>
                                </svg>
                                <span className="fw-bold">Business Loan</span>
                            </div>
                            {/* Car Loan */}
                            <div
                                className={`loan-type-box d-flex flex-column align-items-center justify-content-center p-3 rounded-3 cursor-pointer ${selectedLoanType === 'car' ? 'selected' : ''}`}
                                onClick={() => setSelectedLoanType('car')}
                            >
                                <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" className="bi bi-car-front-fill mb-2" viewBox="0 0 16 16">
                                    <path d="M12.33 3.5a.5.5 0 0 1 .36.14l.87 1.25a.5.5 0 0 1-.36.87H1.5A1.5 1.5 0 0 0 0 6.5v4A1.5 1.5 0 0 0 1.5 12H2a.5.5 0 0 0 .5.5v1a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0 .5-.5h.5a1.5 1.5 0 0 0 1.5-1.5v-4A1.5 1.5 0 0 0 12.33 3.5zM12 10a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM4 10a1 1 0 1 1-2 0 1 1 0 0 1 2 0zM1.5 5.5a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 .5.5v.793a.5.5 0 0 1-.146.353l-1.157 1.157a.5.5 0 0 1-.707 0L9.5 6.707l-.354-.353a.5.5 0 0 0-.708 0L7.5 7.707l-.354-.353a.5.5 0 0 0-.708 0L5.5 7.707l-.354-.353a.5.5 0 0 0-.708 0L3.5 7.707l-.354-.353a.5.5 0 0 0-.708 0L1.5 8.707V5.5z"/>
                                </svg>
                                <span className="fw-bold">Car Loan</span>
                            </div>
                        </div>

                        {/* Dropdowns */}
                        <div className="row g-3 mb-4">
                            <div className="col-md-6">
                                <label htmlFor="financingType" className="form-label text-secondary">Choose your financing type</label>
                                <select
                                    className="form-select rounded-pill px-3 py-2"
                                    id="financingType"
                                    value={financingType}
                                    onChange={(e) => setFinancingType(e.target.value)}
                                >
                                    <option value="">Debt Financing</option>
                                    <option value="equity">Equity Financing</option>
                                    <option value="hybrid">Hybrid Financing</option>
                                </select>
                            </div>
                            <div className="col-md-6">
                                <label htmlFor="bankService" className="form-label text-secondary">Choose your preferred bank service</label>
                                <select
                                    className="form-select rounded-pill px-3 py-2"
                                    id="bankService"
                                    value={bankService}
                                    onChange={(e) => setBankService(e.target.value)}
                                >
                                    <option value="">Individual Banking</option>
                                    <option value="corporate">Corporate Banking</option>
                                    <option value="investment">Investment Banking</option>
                                </select>
                            </div>
                        </div>

                        {/* Loan Amount */}
                        <div className="mb-4">
                            <label htmlFor="loanAmount" className="form-label text-secondary">Your loan amount</label>
                            <div className="input-group mb-3">
                                <span className="input-group-text rounded-start-pill">$</span>
                                <input
                                    type="number"
                                    className="form-control rounded-end-pill"
                                    id="loanAmount"
                                    placeholder="0.00"
                                    value={loanAmount}
                                    onChange={(e) => setLoanAmount(e.target.value)}
                                />
                            </div>
                        </div>

                        {/* Loan Duration */}
                        <div className="mb-4">
                            <label className="form-label text-secondary d-block">Loan duration</label>
                            <div className="d-flex flex-wrap gap-3">
                                {['12 months', '18 months', '24 months', '36 months', '48 months'].map(duration => (
                                    <div className="form-check" key={duration}>
                                        <input
                                            className="form-check-input"
                                            type="radio"
                                            name="loanDuration"
                                            id={`duration${duration.replace(/\s/g, '')}`}
                                            value={duration}
                                            checked={loanDuration === duration}
                                            onChange={(e) => setLoanDuration(e.target.value)}
                                        />
                                        <label className="form-check-label" htmlFor={`duration${duration.replace(/\s/g, '')}`}>
                                            {duration}
                                        </label>
                                    </div>
                                ))}
                            </div>
                        </div>

                        {/* Navigation Button */}
                        <div className="d-flex justify-content-end mt-4">
                            <button className="btn btn-primary rounded-pill px-4 py-2" onClick={handleNext}>
                                Next &rarr;
                            </button>
                        </div>
                    </div>
                );
            case 2:
                return (
                    <div className="loan-form-section">
                        <h4 className="mb-3 text-secondary">Personal Details</h4>
                        <div className="row g-3">
                            <div className="col-md-6 form-floating mb-3">
                                <input type="text" className="form-control" id="firstName" placeholder="First Name" value={firstName} onChange={(e) => setFirstName(e.target.value)} />
                                <label htmlFor="firstName">First Name</label>
                            </div>
                            <div className="col-md-6 form-floating mb-3">
                                <input type="text" className="form-control" id="lastName" placeholder="Last Name" value={lastName} onChange={(e) => setLastName(e.target.value)} />
                                <label htmlFor="lastName">Last Name</label>
                            </div>
                            <div className="col-md-6 form-floating mb-3">
                                <input type="date" className="form-control" id="birthday" placeholder="Birthday" value={birthday} onChange={(e) => setBirthday(e.target.value)} />
                                <label htmlFor="birthday">Birthday</label>
                            </div>
                            <div className="col-md-6 d-flex align-items-center mb-3">
                                <label className='me-3 text-secondary'>Gender:</label>
                                <div className='form-check me-2'>
                                    <input className="form-check-input" type="radio" name="gender" id="genderMale" value="Male" checked={gender === 'Male'} onChange={(e) => setGender(e.target.value)} />
                                    <label className="form-check-label" htmlFor="genderMale">Male</label>
                                </div>
                                <div className='form-check me-2'>
                                    <input className="form-check-input" type="radio" name="gender" id="genderFemale" value="Female" checked={gender === 'Female'} onChange={(e) => setGender(e.target.value)} />
                                    <label className="form-check-label" htmlFor="genderFemale">Female</label>
                                </div>
                                <div className='form-check'>
                                    <input className="form-check-input" type="radio" name="gender" id="genderOther" value="Other" checked={gender === 'Other'} onChange={(e) => setGender(e.target.value)} />
                                    <label className="form-check-label" htmlFor="genderOther">Other</label>
                                </div>
                            </div>
                            {/* Add more personal details fields here */}
                        </div>
                        <div className="d-flex justify-content-between mt-4">
                            <button className="btn btn-secondary rounded-pill px-4 py-2" onClick={() => setCurrentStep(1)}>
                                &larr; Previous
                            </button>
                            <button className="btn btn-primary rounded-pill px-4 py-2" onClick={handleNext}>
                                Next &rarr;
                            </button>
                        </div>
                    </div>
                );
            case 3:
                return (
                    <div className="loan-form-section">
                        <h4 className="mb-3 text-secondary">Documents Upload</h4>
                        <p className="text-muted mb-4">Please upload the required documents for your loan application.</p>
                        <div className="mb-3">
                            <label htmlFor="document1" className="form-label text-secondary">Upload ID Document (e.g., Passport, Driver's License)</label>
                            <input className="form-control" type="file" id="document1" onChange={(e) => setDocument1(e.target.files[0])} />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="document2" className="form-label text-secondary">Upload Proof of Income (e.g., Pay Stubs, Bank Statements)</label>
                            <input className="form-control" type="file" id="document2" onChange={(e) => setDocument2(e.target.files[0])} />
                        </div>
                        {/* Add more document upload fields as needed */}
                        <div className="d-flex justify-content-between mt-4">
                            <button className="btn btn-secondary rounded-pill px-4 py-2" onClick={() => setCurrentStep(2)}>
                                &larr; Previous
                            </button>
                            <button className="btn btn-success rounded-pill px-4 py-2" onClick={handleSubmit}>
                                Submit Application
                            </button>
                        </div>
                    </div>
                );
            default:
                return null;
        }
    };

    return (
        <div className="loan-form-page-wrapper min-vh-100 d-flex justify-content-center align-items-center py-5">
            <div className="loan-form-card card shadow-lg rounded-4 overflow-hidden">
                <div className="row g-0 h-100"> {/* Use h-100 to make row take full height of card */}

                    {/* Left Column: Step Navigation */}
                    <div className="col-md-3 loan-steps-sidebar p-4 d-flex flex-column justify-content-start align-items-start">
                        <div className={`loan-step mb-4 ${currentStep === 1 ? 'active' : ''}`}>
                            <div className="step-circle bg-primary text-white d-flex justify-content-center align-items-center fw-bold">1</div>
                            <span className="step-text text-primary ms-3 fw-bold">Loan Details</span>
                        </div>
                        <div className={`loan-step mb-4 ${currentStep === 2 ? 'active' : ''}`}>
                            <div className="step-circle border border-secondary text-secondary d-flex justify-content-center align-items-center fw-bold">2</div>
                            <span className="step-text text-secondary ms-3">Personal Details</span>
                        </div>
                        <div className={`loan-step mb-4 ${currentStep === 3 ? 'active' : ''}`}>
                            <div className="step-circle border border-secondary text-secondary d-flex justify-content-center align-items-center fw-bold">3</div>
                            <span className="step-text text-secondary ms-3">Documents Upload</span>
                        </div>
                    </div>

                    {/* Right Column: Form Content */}
                    <div className="col-md-9 loan-form-content p-5 d-flex flex-column justify-content-between">
                        <div> {/* Wrapper div for form content and heading */}
                            {renderStepContent()}
                        </div>
                        {/* Navigation buttons are now inside renderStepContent for each step */}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default LoanApplicationForm;