import React from "react";
import axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './login.scss'
import $ from 'jquery';
import LoadingButton from '@mui/lab/LoadingButton';
import {TextField,Grid,Paper,Typography} from "@material-ui/core";


export default function Login() {
  const [loading, setLoading] = React.useState(false);
  const [authError, setAuthError] = React.useState("");
  
  var passwordLenght = 6;
  var userLenght = 5;

  var _user;
  var _password;


  function getAuth() {
    var body = { username: _user, password: _password };
    var headers = { 'Content-Type': 'application/json' };
    axios.post('http://localhost:8080/auth', body, { headers })
      .then(response => {
        localStorage.setItem("token", response.data.token);
        window.location.reload(false);
      })
      .catch(error => {
        loginError();
        setLoading(false);
      })
      ;
  }

  const onBtnClick = e => {
    if (_user && _password) {
      getAuth();
    } else {
      loginError();
    }
  }

  const loginError = () => {
    toast.error("User or password incorrect");
  }
  
  function handleClick() {
    setLoading(true);
    var err = "";
    _user = $('#user').val();
    if (!_user || _user.length < userLenght) {
      err = err + "user";
    }
    _password = $('#password').val();
    if (!_password || _password.length < passwordLenght) {
      err = err + "password";
    }
    setAuthError(err);
    if (err === "") {
      onBtnClick();
    }else{
      setLoading(false);
    }
  }

  return (
    <div>
      <Grid container spacing={0} className="justify-center" direction="row">
        <Grid item xs={4}>
          <Grid
            container
            direction="column"
            spacing={2}
            className="login-form justify-center"
          >
            <Paper
              variant="elevation"
              elevation={2}
              className="login-background"
            >
              <Grid item>
                <Typography component="h1" variant="h5">
                  Sign in
                </Typography>
              </Grid>
              <Grid item >
                <Grid container direction="column" spacing={2}>
                  <Grid item>
                    <TextField
                      id="user"
                      placeholder="User"
                      fullWidth
                      name="user"
                      label="Username"
                      variant="filled"
                      helperText={authError.includes("user") ? "Your username must be at least " + userLenght + " characters" : ""}
                      error={authError.includes("user")}
                      autoFocus
                    />
                  </Grid>
                  <Grid item>
                    <TextField
                      id="password"
                      type="password"
                      placeholder="Password"
                      fullWidth
                      name="password"
                      label="Password"
                      variant="filled"
                      helperText={authError.includes("password") ? "Your password must be at least " + passwordLenght + " characters" : ""}
                      error={authError.includes("password")}
                    />
                  </Grid>
                  <Grid item>
                    <LoadingButton
                      onClick={handleClick}
                      loading={loading}
                      variant="contained"
                      className="button-block"
                      color="primary"
                    >
                      Submit
                    </LoadingButton>
                  </Grid>
                </Grid>
              </Grid>
            </Paper>
          </Grid>
        </Grid>
      </Grid>
    </div>


  );
};