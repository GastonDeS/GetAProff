import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'
import axios from 'axios'

import { Card, CardBody, Description, Name, Price, TutorImg } from './TutorCard.styles'
import RatingStar from 'react-stars';
import Profile from '../../assets/img/profile.png'

const TutorCard = ({ user }) => {
  const [image, setImage] = useState(Profile);
  const [error, setError] = useState();

  useEffect(() => {
    axios.get('images/' + user.id)
    .then(res => {
      setImage('data:image/png;base64,' + res.data.image);
    })
    .catch(error => setError(error));
  }, [])

  return (
    <Card>
      <TutorImg variant="top" src={image} alt='tutorImage'/> 
      <CardBody>
        <Name>{user.name}</Name>
        <Description>{user.description}</Description>
        <RatingStar count={5} value={user.rate} size={15} edit={false}/>
        {
          user.minPrice === user.maxPrice ? <Price>${user.minPrice}</Price> :
          <Price>${user.minPrice} - ${user.maxPrice}</Price>
        }
      </CardBody>
    </Card>
  )
}

TutorCard.propTypes = {
  user: PropTypes.object
}

export default TutorCard