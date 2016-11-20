/**
 * Created by dkiss on 2016. 11. 20..
 */

$(function() {
    var dropdown = document.getElementById("resourceTypeID");
    var id = dropdown.options[dropdown.selectedIndex].value;
    if(id == -1) {
        var resourceDropdown = document.getElementById("resourceID");
        resourceDropdown.disabled = true;
    }
    else {
        AJAXRequest();
    }
});

$("#resourceTypeID").change(function () {
    AJAXRequest();
});

function AJAXRequest() {
    var dropdown = document.getElementById("resourceTypeID");
    var id = dropdown.options[dropdown.selectedIndex].value;
    $.ajax(
        {
            url: "/requests/getResources/" + id,
            success: function(result) {
                var resourceDropdown = document.getElementById("resourceID");
                resourceDropdown.disabled = false;
                $("#resourceID").empty();
                $.each(result.resources, function(i, item)
                {
                    var o = new Option(item.name, item.id);
                    $("#resourceID").append(o);
                });
            }
        }
    )
}