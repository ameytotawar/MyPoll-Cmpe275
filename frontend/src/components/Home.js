import React, { Component } from 'react';
import { 
  Link,
  withRouter,
  BrowserRouter as Router,
  Route,
} from 'react-router-dom';
import { connect } from 'react-redux';

import { userSignOutRequest } from '../actions';

import {
  Container, 
  Button,
  Divider,
} from 'semantic-ui-react';
import styled from 'styled-components';

import NavbarHome from './NavbarHome';
import SurveyForm from './surveys/SurveyForm';
import SurveyList from './surveys/SurveyList';
import SurveyDetail from './surveys/SurveyDetail';
import SurveyTaking from './surveys/SurveyTaking';
import SurveyTakingDetail from './surveys/SurveyTakingDetail';


const MyContainer = styled.div`
  width: 100%;
  height: 100%;
  margin: 0px;
  padding: 0px;
  ${'' /* background: #0099FF; */}

`;
class Home extends Component {

  componentDidMount() {
    if(localStorage.getItem('token') == null) {
      this.props.history.push('/');
    }
  }


  handleSignOut = () => {
    console.log('Home handleSignOut');
    this.props.userSignOutRequest();
  }

  render() {
    return (
      <MyContainer>
        <NavbarHome />
        <Container>
          {/* <h2>Home</h2> */}
          {/* <Button onClick={this.handleSignOut}>Sign out</Button> */}

          {/* <SurveyForm /> */}

          <Divider />
          {/* <SurveyList /> */}

          <Route exact path='/home' component={SurveyList} />
          <Route path='/home/surveydetail' component={SurveyDetail} />
          <Route path='/home/surveyee' component={SurveyTaking} />

          <Route path='/home/takesurvey/:id' component={SurveyTakingDetail} />
          {/* <Route path='/home/sharing' component={Sharing} />
          <Route path='/home/starred' component={Main} />
          <Route path='/home/activities' component={Activities} /> */}


        </Container>

      </MyContainer>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    is_authenticated: state.UserReducer.is_authenticated,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    userSignOutRequest: () => { dispatch(userSignOutRequest()); },
  }
}

const connectedHome = withRouter(connect(mapStateToProps,mapDispatchToProps)(Home));

export default connectedHome;
