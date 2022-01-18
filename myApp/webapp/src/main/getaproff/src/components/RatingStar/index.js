import React from 'react'
import PropTypes from 'prop-types'
import styled from 'styled-components'

const RatingStar = ({ rating }) => {
  const Wrapper = styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    width: fit-content;
    height: fit-content;
  `;

  const items = [];

  for (let index = 0; index < 5; index++) {
    if (rating - index >= 1) {
      items.push(<span className={'fa fa-star checked'} style={{color: 'orange'}}></span>);
    } else if (rating - index > 0 && rating - index < 1) {
      items.push(<span className={'fa fa-star-half-o checked'} style={{color: 'orange'}}></span>);
    } else {
      items.push(<span class={'fa fa-star-o checked'} style={{color: 'orange'}}></span>);
    }
  }

  return (
    <Wrapper>
      {items}
    </Wrapper>
  );
}

RatingStar.propTypes = {
  rating: PropTypes.number
}

export default RatingStar