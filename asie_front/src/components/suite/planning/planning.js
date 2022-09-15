import * as React from 'react';
import { useState, useEffect } from 'react';
import Box from '@mui/material/Box';
import Collapse from '@mui/material/Collapse';
import IconButton from '@mui/material/IconButton';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Typography from '@mui/material/Typography';
import Paper from '@mui/material/Paper';
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import TablePagination from '@mui/material/TablePagination';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { NavLink } from 'react-router-dom';
import { toast } from 'react-toastify';
import { useBetween } from 'use-between';
import Button from '@mui/material/Button';

import axios from 'axios';

const useShareableState = () => {
  const [prodNameDeleted, setProdNameDeleted] = useState(false);
  return {
    prodNameDeleted,
    setProdNameDeleted
  }
}

function Row(props) {
  const { row } = props;
  const [open, setOpen] = React.useState(false);
  const deleted = () => toast.success(`Product ${row.name} deleted successfully`);
  const generalError = () => toast.error("Something went wrong");
  const { prodNameDeleted, setProdNameDeleted } = useBetween(useShareableState);

  async function deleteProduct() {
    var headers = { 'Authorization': localStorage.getItem("token") };
    axios.delete(`http://localhost:8080/products/${row.id}`, { headers })
      .then(response => {
        deleted();
        setProdNameDeleted(true)
      })
      .catch(errorM => {
        _status = errorM.response.status;
        if (_status === 403) {
          unauthorized();
        } else if (_status >= 400) {
          generalError();
        }
      });

  }

  return (
    <React.Fragment>
      <TableRow sx={{ '& > *': { borderBottom: 'unset' } }}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpen(!open)}
          >
            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        <TableCell component="th" scope="row">
          {row.name}
        </TableCell>
        <TableCell>{row.description}</TableCell>
        <TableCell>
        <IconButton aria-label="delete" onClick={() => deleteProduct()}>
          <DeleteIcon />
        </IconButton>
        <IconButton aria-label="edit" color="primary" key={"Home"} component={NavLink} to={`/product-details/${row.id}`}>
          
          <EditIcon />
        </IconButton>
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={6}>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <Box sx={{ margin: 1 }}>
              <Typography variant="h6" gutterBottom component="div">
                Statistics
              </Typography>
              <Table size="small" aria-label="purchases">
                <TableHead>
                  <TableRow>
                    <TableCell>Name</TableCell>
                    <TableCell>Description</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {row.prodStatisticsList.map((historyRow) => (
                    <TableRow key={historyRow.name}>
                      <TableCell component="th" scope="row">
                        {historyRow.name}
                      </TableCell>
                      <TableCell>{historyRow.description}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            </Box>
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}


export default function Planning() {
  const [isDeleted, setDelete] = React.useState(false);
  const [auth, setAuth] = React.useState(null);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [page, setPage] = React.useState(0);
  const [order, setOrder] = React.useState('asc');
  const [orderBy, setOrderBy] = React.useState('none');
  const { prodNameDeleted, setProdNameDeleted } = useBetween(useShareableState);

  useEffect(() => {
    getProducts();
  }, [])

  useEffect(() => {
    if (prodNameDeleted) {
      getProducts();
      setProdNameDeleted(false)
    }
  }, [prodNameDeleted])

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  function stableSort(array, comparator) {
    const stabilizedThis = array.map((el, index) => [el, index]);
    stabilizedThis.sort((a, b) => {
      const order = comparator(a[0], b[0]);
      if (order !== 0) {
        return order;
      }
      return a[1] - b[1];
    });
    return stabilizedThis.map((el) => el[0]);
  }

  function getComparator(order, orderBy) {
    return order === 'desc'
      ? (a, b) => descendingComparator(a, b, orderBy)
      : (a, b) => -descendingComparator(a, b, orderBy);
  }

  function descendingComparator(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
      return -1;
    }
    if (b[orderBy] > a[orderBy]) {
      return 1;
    }
    return 0;
  }

  


  async function getProducts() {
    var headers = { 'Authorization': localStorage.getItem("token") };
    axios.get('http://localhost:8080/products', { headers })
      .then(response => {
        setAuth(response.data);
      })
      .catch(errorM => {
        setAuth(null);
      });
  }

  if (auth && Object.keys(auth).length > 0) {
    return (
      <div>
        <Box sx={{ width: '100%' }}>
        <Button variant="contained" component={NavLink} to="/create-new-product">Create</Button>
          <Paper sx={{ width: '100%', mb: 2 }}></Paper>
          <TableContainer component={Paper}>
            <Table aria-label="collapsible table">
              <TableHead>
                <TableRow>
                  <TableCell />
                  <TableCell>Name</TableCell>
                  <TableCell>Description</TableCell>
                  <TableCell>Actions</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {stableSort(auth, getComparator(order, orderBy))
                  .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                  .map((row) => (
                    <Row key={row.name} row={row} />
                  ))}
              </TableBody>
            </Table>
          </TableContainer>
          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={auth.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Box>
      </div >
    );
  }
  return (
    <Typography sx={{ mb: 1.5 }} color="text.secondary">
      No Product found
    </Typography>
  );
}
