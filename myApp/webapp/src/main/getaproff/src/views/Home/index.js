import React, {useHistory} from 'react'
import PropTypes from 'prop-types'
import { Wrapper } from './Home.styles'
import Navbar from '../../components/Navbar'

const Home = () => {

  return (
    <Wrapper>
      <Navbar/>
    </Wrapper>
  )
}

Home.propTypes = {

}

export default Home
