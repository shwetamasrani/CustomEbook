import axios from 'axios';

const USER_API_BASE_URL="http://localhost:8081/api/user"
class UserService{

    getUser()
    {
        return axios.get(USER_API_BASE_URL);   //get the data from the API mentioned
    }

    createUser(user)
    {
        console.log("Creating user", user);
        return axios.post(USER_API_BASE_URL,user);

    }
}
export default new UserService()   //exporting the object of this class