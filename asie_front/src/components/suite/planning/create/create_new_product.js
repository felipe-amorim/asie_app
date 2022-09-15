import React, {useEffect} from 'react';
import axios from 'axios';
import $ from 'jquery';
import './create_new_product.scss';
import { toast } from 'react-toastify';
import { NavLink } from 'react-router-dom';

import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Toolbar from '@mui/material/Toolbar';
import TextField from '@mui/material/TextField';
import LoadingButton from '@mui/lab/LoadingButton';

export default function CreateNewProduct() {
  const created = () => toast.success("Product " + _name + " created successfully");
  const unauthorized = () => toast.error("Unauthorized");
  const conflictExists = () => toast.error("Product name already exists");
  const generalError = () => toast.error("Something went wrong");

  const [authError, setAuthError] = React.useState("");
  const [loading, setLoading] = React.useState(false);

  var _name;
  var _description;
  var nameLenght = 6;
  var descriptionLenght = 6;

  function handleClick() {
    setLoading(true);
    let err = "";
    _name = $('#name').val();
    if (!_name || _name.length < nameLenght) {
      err = err + "name";
    }
    _description = $('#description').val();
    if (!_description || _description.length < descriptionLenght) {
      err = err + "description";
    }
    setAuthError(err);
    if (err === "" && _name && _description) {
      postProduct();
    } else {
      setLoading(false);
    }
  }

  async function postProduct() {
    let _status;
    var body = { name: _name, description: _description, prodStatisticsList:[{name:'mock', description:'mock'}] };
    var headers = { 'Content-Type': 'application/json', 'Authorization': localStorage.getItem("token") };
    axios.post('http://localhost:8080/products', body, { headers })
      .then(response => {
        $("#cancel")[0].click();
        created();
      })
      .catch(errorM => {
        _status = errorM.response.status;
        if (_status === 403) {
          unauthorized();
        } else if (_status === 409) {
          conflictExists();
        } else if (_status >= 400) {
          generalError();
        }
      })
      .finally(errorM => {
        setLoading(false);
      });
  }

  return (
    <div>
      <Typography variant="h5" component="div">
        New Products
      </Typography>
      <hr />
      <div>
        <Grid item xs={4}
          sx={{ marginTop: 2 }}>
          <Grid container direction="column" spacing={2}>
            <Grid item>
              <TextField
                id="name"
                placeholder="Name"
                fullWidth
                name="name"
                label="Name"
                variant="filled"
                helperText={authError.includes("name") ? "The product name must be at least " + nameLenght + " characters" : ""}
                error={authError.includes("name")}
                autoFocus
              />
            </Grid>
            <Grid item>
              <TextField
                id="description"
                type="description"
                placeholder="Description"
                fullWidth
                multiline
                rows={4}
                name="description"
                label="Description"
                variant="filled"
                helperText={authError.includes("description") ? "The product description must be at least " + descriptionLenght + " characters" : ""}
                error={authError.includes("description")}
              />
            </Grid>
            <Grid item>
              <Toolbar className="right-content">
                <Button
                  id="cancel"
                  variant="contained"
                  color="error"
                  type="submit"
                  sx={{ minWidth: 150, margin: 1 }}
                  component={NavLink} to="/planning"
                >
                  Cancel
                </Button>
                <LoadingButton
                  id="submit"
                  onClick={handleClick}
                  loading={loading}
                  variant="contained"
                  className="button-block"
                  sx={{ minWidth: 150, margin: 1 }}
                  color="primary"
                >
                  Submit
                </LoadingButton>
              </Toolbar>
            </Grid>
          </Grid>
        </Grid>
      </div>
    </div>

  );
};