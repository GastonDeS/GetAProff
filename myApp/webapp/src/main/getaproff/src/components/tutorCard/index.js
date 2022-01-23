import React from 'react'
import PropTypes from 'prop-types'
import { Card, CardBody, Description, Name, Price, TutorImg } from './TutorCard.styles'
import RatingStar from '../RatingStar'

const TutorCard = ({ name, description, rating, minPrice, maxPrice, image }) => {

  return (
    <Card>
      <TutorImg variant="top" src={image} alt='tutorImage'/> 
      <CardBody>
        <Name>{name}</Name>
        <Description>{description}</Description>
        <RatingStar rating={rating}/>
        {
          minPrice === maxPrice ? <Price>${minPrice}</Price> :
          <Price>${minPrice} - ${maxPrice}</Price>
        }
      </CardBody>
    </Card>
  )
}

TutorCard.propTypes = {
  name: PropTypes.string,
  description: PropTypes.string,
  rating: PropTypes.number,
  minPrice: PropTypes.number,
  maxPrice: PropTypes.number
}

export default TutorCard