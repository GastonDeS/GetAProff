import React, { useEffect, useState } from 'react'
import PropTypes from 'prop-types'

import { Card, CardBody, Description, Name, Price, TutorImg } from './TutorCard.styles'
import RatingStar from 'react-stars';
import ProfileImg from '../../assets/img/no_profile_pic.jpeg';
import { useNavigate } from 'react-router-dom';
import { userService } from '../../services';
import { handleService } from '../../handlers/serviceHandler';

const TutorCard = ({ user }) => {
  const [image, setImage] = useState(ProfileImg);

  const navigate = useNavigate();

  useEffect(async () => {
    const res = await userService.getUserImg(user.id);
    const data = handleService(res, navigate);
    if (data) setImage('data:image/png;base64,' + data.image);
  }, []);

  return (
    <Card onClick={() => navigate('/users/' + user.id)}>
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