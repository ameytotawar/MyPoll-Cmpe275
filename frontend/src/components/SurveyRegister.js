import React, { Component } from 'react';
import {
  Link,
} from 'react-router-dom';
import { connect } from 'react-redux';

import {
  axiosSignIn,
} from '../actions';

import {
  Container,
  Form,
  Button,
  Grid,
  Message,
  Header,
} from 'semantic-ui-react';
import styled from 'styled-components';

import Navbar from './Navbar';

import axios from 'axios';


const MyContainer = styled.div`
width: 100%;
height: 100%;
margin-left: 0px;
padding: 0px;
${'' /* background: #0099FF; */}

`;


const ErrorMessage = ({formErrors}) => (
  <Container>
    <div></div>
    {Object.keys(formErrors).map((fieldName, i) => {
      if (formErrors[fieldName].length > 0) {
        return (
         
          <Message negative>
            <p key={i}>{fieldName} {formErrors[fieldName]}</p>
          </Message>

        );
      }
    })}
  </Container>
);

class SurveyRegister extends Component {
  constructor(props) {
    super(props);
    this.state = {
      firstname: '',
      lastname: '',
      email: '',
      password: '',
      formErrors: { Token: '' },
      emailValid: false,
      passwordValid: false,
      firstnameValid: true,
      lastnameValid: true,
      formValid: false,
      token: '',
      tokenValid: true
    }
  }
  
  validateField(fieldName) {
    const formErrorsValidation = this.state.formErrors;
    
    let tokenValid = this.state.tokenValid;
    
    switch (fieldName) {
      case 'token':
        formErrorsValidation.Token = tokenValid ? '' : ' Verification code is invalid. Please Try again';
        break;
      default:
        break;
    }
    // update the error message
    this.setState({
      formErrors: formErrorsValidation,
      tokenValid
    }, this.validateForm);
  }

  validateForm() {
    this.setState({ formValid: this.state.tokenValid });
  }

  handleRegister(e) {
    e.preventDefault();
    console.log('handleSignIn', this.state.token);
    // this.props.axiosVerify(this.state.token, this.props.history);
    axios.put('http://localhost:8300/user/register'+this.state.token).then( res => {
      console.log("responseeee");
      if(res.status===200)
        this.props.history.push('/signin');
      else {
        console.log("error 404");
        this.setState({
          tokenValid: false,
        }, () => {
          this.validateField('token');
        });
      }

    })
    .catch( res => {
      console.log("error 404");
        this.setState({
          tokenValid: false,
        }, () => {
          this.validateField('token', this.state.token);
        });
    })
    // // this.props.history.push('/signin');
  }

  handleChange(e) {
    const target = e.target;

    console.log(`handleChange ${target.name}=[${target.value}]`);

    // validate field everytime user enters something.
    this.setState({
      [target.name]: target.value,
      tokenValid: true
    }, () => {
      this.validateField(target.name, target.value);
    });
  }


  render() {
    return (
      <MyContainer>
        <Navbar />

        <Container>
          <Grid>
            <Grid.Row>
              <Grid.Column width={4} />
              <Grid.Column width={8}>
                <Header size='huge'>Register for the Survey</Header>
                <h2>Enter your Email ID or Sign In</h2>
              </Grid.Column>
              <Grid.Column width={4} />
            </Grid.Row>
          </Grid>

          <Grid>
            <Grid.Row>
              <Grid.Column width={4}>
              </Grid.Column>
              <Grid.Column width={8}>
              
                <Form onSubmit={ (e) => { this.handleRegister(e) }} >
                  <Form.Field>
                    <label>Enter Email</label>
                    <input type='password' placeholder='Email' name='token' value={this.state.token} onChange={ (e) => { this.handleChange(e); }}/>
                  </Form.Field>
      
                  <Button color='youtube' type='submit' disabled={!this.state.formValid}>Submit</Button>
                </Form>

                <ErrorMessage formErrors={this.state.formErrors} />
              </Grid.Column>
              <Grid.Column width={4}>
              </Grid.Column>
            </Grid.Row>
          </Grid>





        </Container>
      </MyContainer>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    axiosVerify: (data, router) => { dispatch(axiosSignIn(data, router)); },
  };
};

// const connectedSignIn = connect(mapStateToProps,mapDispatchToProps)(SignIn);
export default SurveyRegister;
