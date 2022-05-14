import React, { useState } from 'react'
import { Wrapper } from './SearchBar.styles'
import PropTypes from "prop-types";
// Icon
import SearchIcon from '../../assets/img/search_icon.png'
import Button from "../Button";
import {useNavigate} from "react-router-dom";
import i18next from "i18next";

const SearchBar = ({register, name, getValues}) => {
  const [state, setState] = useState(getValues('search'));
  let navigate = useNavigate()

  const search = () => {
      navigate(`/tutors?search=${state}`)
  }

  return (
    <Wrapper>
        <img src={SearchIcon} alt='search-icon' />
        <input
            {...register(name)}
              type='text'
              placeholder={i18next.t('searchBar.placeholder')}
              onChange={event => setState(event.currentTarget.value)}
              value={state}
        />
        <Button text={i18next.t('searchBar.search')} callback={search}/>
    </Wrapper>
  )
}

SearchBar.propTypes = {
    register : PropTypes.func,
    name: PropTypes.string,
    getValues: PropTypes.func,
}

SearchBar.defaultProps = {
    register: (x) => x,
    getValues: x => '',
    name: ""
}

export default SearchBar