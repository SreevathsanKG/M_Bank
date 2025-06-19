import { configureStore } from "@reduxjs/toolkit";
import UserReducer from "./reducers/UserReducer";
import UserInfoReducer from "./reducers/UserInfoReducer";

const store = configureStore({
    reducer: {
        user: UserReducer,
        userInfo : UserInfoReducer
    }
})

export default store